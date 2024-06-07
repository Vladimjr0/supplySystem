package com.shevcov.suplysystem.controller;

import com.shevcov.suplysystem.dto.ReportSupplyDto;
import com.shevcov.suplysystem.dto.SupplyReportItemDto;
import com.shevcov.suplysystem.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Контроллер для создания отчетов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Метод позволяющий вывести отчет по поставкам за определенное время")
    @GetMapping
    public ResponseEntity<ReportSupplyDto> getReportByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(reportService.getReport(startDate, endDate));
    }

    @Operation(summary = "Метод позволяющий вывести отчет по поставкам от конкретного поставщика")
    @GetMapping("/{supplierId}")
    public ResponseEntity<ReportSupplyDto> getReportBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(reportService.getReportBySupplier(supplierId));
    }

    @Operation(summary = "Метод позволяющий скачать отчет по поставкам за определенное время в виде Excel файла")
    @GetMapping("/excel")
    public ResponseEntity<ByteArrayResource> downloadReportAsExcel(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {

        byte[] data = reportService.generateExcelReport(startDate, endDate);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
