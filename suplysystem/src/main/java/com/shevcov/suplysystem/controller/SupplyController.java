package com.shevcov.suplysystem.controller;


import com.shevcov.suplysystem.dto.SupplyRequestDto;
import com.shevcov.suplysystem.entity.Supply;
import com.shevcov.suplysystem.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@Tag(name = "Контроллер для взаимодействия с поставками")
@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @Operation(summary = "Метод для создания поставки")
    @PostMapping
    public ResponseEntity<Supply> createSupply(@Valid @RequestBody SupplyRequestDto supplyRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplyService.createSupply(supplyRequestDto));

    }

    @Operation(summary = "Метод для просмотра всего списка поставок")
    @GetMapping("/list")
    public ResponseEntity<List<Supply>> getAllSupply() {
        return ResponseEntity.ok(supplyService.getAllSupply());
    }

    @Operation(summary = "Метод для удаления поставки по Id")
    @DeleteMapping("/{supplyId}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long supplyId) {
        supplyService.deleteSupplyById(supplyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для просмотра одной конкретной поставки по Id")
    @GetMapping("/list/{supplyId}")
    public ResponseEntity<Supply> getSupply(@PathVariable Long supplyId) {
        return ResponseEntity.ok(supplyService.getSupplyById(supplyId));
    }

    @Operation(summary = "Метод для обновления информации о поставке")
    @PutMapping("/{supplyId}")
    public ResponseEntity<Void> updateSupply(@PathVariable Long supplyId,
                                             @Valid @RequestBody SupplyRequestDto supplyRequestDto){
        supplyService.updateSupply(supplyRequestDto, supplyId);
        return ResponseEntity.noContent().build();
    }

}
