package com.shevcov.suplysystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SupplyRequestDto {

    private Long supplierId;

    @NotNull(message = "Список товаров не может быть пустым")
    private List<SupplyItemRequestDto> items;


}
