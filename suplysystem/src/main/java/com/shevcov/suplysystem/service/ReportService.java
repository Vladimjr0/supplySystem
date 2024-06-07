package com.shevcov.suplysystem.service;

import com.shevcov.suplysystem.dto.ReportSupplyDto;
import com.shevcov.suplysystem.dto.SupplyReportItemDto;
import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.entity.Supplier;
import com.shevcov.suplysystem.entity.Supply;
import com.shevcov.suplysystem.entity.SupplyItem;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SupplyService supplyService;

    @Value("${report.download.directory}")
    private String reportDownloadDirectory;

    public ReportSupplyDto getReport(LocalDate startDate, LocalDate endDate) {
        List<Supply> supplies = supplyService.getSuppliesByDateRange(startDate, endDate);
        return generateReport(supplies);
    }

    public ReportSupplyDto getReportBySupplier(Long supplierId) {
        List<Supply> supplies = supplyService.getSupplyBySupplier(supplierId);
        return generateReport(supplies);
    }

    private ReportSupplyDto generateReport(List<Supply> supplies) {
        Map<String, SupplyReportItemDto> reportItemMap = new HashMap<>();

        double totalOverallWeight = 0.0;
        double totalOverallPrice = 0.0;

        for (Supply supply : supplies) {
            for (SupplyItem item : supply.getItems()) {
                String key = generateKey(supply.getSupplier(), item.getProduct());

                SupplyReportItemDto reportItem = reportItemMap.getOrDefault(key, new SupplyReportItemDto());
                reportItem.setSupplier(supply.getSupplier());
                reportItem.setProduct(item.getProduct());
                reportItem.setTotalWeight(item.getWeight());
                reportItem.setTotalPrice(item.getPrice());
                reportItem.setSupplyDate(supply.getSupplyDate());

                reportItemMap.put(key, reportItem);

                totalOverallPrice += item.getPrice();
                totalOverallWeight += item.getWeight();
            }
        }
        ReportSupplyDto report = new ReportSupplyDto();
        report.setReportItems(new ArrayList<>(reportItemMap.values()));
        report.setFinalWeight(totalOverallWeight);
        report.setFinalPrice(totalOverallPrice);

        return report;
    }

    private String generateKey(Supplier supplier, Product product) {
        return supplier.getId() + "_" + product.getId();
    }

    public byte[] generateExcelReport(LocalDate startDate, LocalDate endDate) throws IOException {
        ReportSupplyDto reportSupplyDto = getReport(startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Supply Report");

            int rowIdx = 0;
            Row headerRow = sheet.createRow(rowIdx++);
            headerRow.createCell(0).setCellValue("Supplier");
            headerRow.createCell(1).setCellValue("Product");
            headerRow.createCell(2).setCellValue("Total Weight");
            headerRow.createCell(3).setCellValue("Total Price");
            headerRow.createCell(4).setCellValue("Supply Date");

            for (SupplyReportItemDto item : reportSupplyDto.getReportItems()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(item.getSupplier().getName());
                row.createCell(1).setCellValue(item.getProduct().getName());
                row.createCell(2).setCellValue(item.getTotalWeight());
                row.createCell(3).setCellValue(item.getTotalPrice());
                row.createCell(4).setCellValue(item.getSupplyDate().toString());
            }

            Row totalRow = sheet.createRow(rowIdx);
            totalRow.createCell(0).setCellValue("Итого");
            totalRow.createCell(2).setCellValue(reportSupplyDto.getFinalWeight());
            totalRow.createCell(3).setCellValue(reportSupplyDto.getFinalPrice());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
}



