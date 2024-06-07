package com.shevcov.suplysystem.dto;

import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.entity.Supplier;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SupplyReportItemDto {

    private Supplier supplier;
    private Product product;
    private Double totalWeight;
    private Double totalPrice;
    private LocalDate supplyDate;




}
