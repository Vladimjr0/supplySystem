package com.shevcov.suplysystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank(message = "Имя поставщика не может быть пустым")
    private String name;


}
