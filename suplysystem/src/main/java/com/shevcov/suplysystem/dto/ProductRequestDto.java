package com.shevcov.suplysystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Название товара не может быть пустым")
    private String name;

    @NotBlank(message = "Категория товара не может быть пустой")
    private String type;


}
