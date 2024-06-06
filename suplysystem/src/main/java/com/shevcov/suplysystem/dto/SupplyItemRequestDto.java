package com.shevcov.suplysystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SupplyItemRequestDto {

    private  Long productId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Масса товара должна быть больше нуля")
    @NotNull(message = "Это поле обязательное")
    private Double weight;

    @DecimalMin(value = "0.0", inclusive = false, message = "Цена товара должна быть больше нуля")
    @NotNull(message = "Это поле обязательное")
    private Double price;

}
