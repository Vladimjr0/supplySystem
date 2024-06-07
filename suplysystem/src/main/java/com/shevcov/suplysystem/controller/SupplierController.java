package com.shevcov.suplysystem.controller;

import com.shevcov.suplysystem.dto.SupplierRequestDto;
import com.shevcov.suplysystem.entity.Supplier;
import com.shevcov.suplysystem.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер для взаимодействия с Поставщиками")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Метод для создания поставщика")
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(supplierRequestDto));
    }

    @Operation(summary = "Метод для просмотра списка всех поставщиков",
               description = "Выводит список всех поставщиков и сортирует этот список в алфавитном порядке")
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSupplier(){
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }

    @Operation(summary = "Метод для удаления поставщика по Id")
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long supplierId){
        supplierService.deleteSupplierById(supplierId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для обновления поставщика по Id")
    @PutMapping("/{supplierId}")
    public ResponseEntity<Void> updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody SupplierRequestDto supplierRequestDto){
        supplierService.updateSupplierById(supplierId, supplierRequestDto);
        return ResponseEntity.noContent().build();
    }

}
