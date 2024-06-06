package com.shevcov.suplysystem.service;

import com.shevcov.suplysystem.dto.ReportSupplyDto;
import com.shevcov.suplysystem.dto.SupplyReportItemDto;
import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.entity.Supplier;
import com.shevcov.suplysystem.entity.Supply;
import com.shevcov.suplysystem.entity.SupplyItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SupplyService supplyService;

    public ReportSupplyDto getReport(LocalDate startDate, LocalDate endDate){
        List<Supply> supplies = supplyService.getSuppliesByDateRange(startDate, endDate);
        return generateReport(supplies);
    }

    public ReportSupplyDto getReportBySupplier(Long supplierId){
        List<Supply> supplies = supplyService.getSupplyBySupplier(supplierId);
        return generateReport(supplies);
    }

    private ReportSupplyDto generateReport(List<Supply> supplies){
        Map<String, SupplyReportItemDto> reportItemMap = new HashMap<>();

        double totalOverallWeight = 0.0;
        double totalOverallPrice = 0.0;

        for(Supply supply: supplies){
            for (SupplyItem item: supply.getItems()){
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

    private String generateKey(Supplier supplier, Product product){
        return supplier.getId() + "_" + product.getId();
    }

}


