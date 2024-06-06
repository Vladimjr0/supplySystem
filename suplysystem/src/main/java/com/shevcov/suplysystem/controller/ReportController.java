package com.shevcov.suplysystem.controller;

import com.shevcov.suplysystem.dto.ReportSupplyDto;
import com.shevcov.suplysystem.dto.SupplyReportItemDto;
import com.shevcov.suplysystem.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Контроллер для создания отчетов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    //TODO так же стоит обрабатывать ошибку неправильного ввода даты
    //TODO стоит добавить метод, для скачивания отчета в виде файла таблиц
    @Operation(summary = "Метод позволяющий вывести отчет по поставкам за определенное время")
    @GetMapping
    public ResponseEntity<ReportSupplyDto> getReportByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(reportService.getReport(startDate, endDate));
    }

    @Operation(summary = "Метод позволяющий вывести отчет по поставкам от конкретного поставщика")
    @GetMapping("/{supplierId}")
    public ResponseEntity<ReportSupplyDto> getReportBySupplier(@PathVariable Long supplierId){
        return ResponseEntity.ok(reportService.getReportBySupplier(supplierId));
    }

}
