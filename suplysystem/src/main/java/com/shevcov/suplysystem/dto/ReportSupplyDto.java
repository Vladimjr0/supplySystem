package com.shevcov.suplysystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportSupplyDto {

    private List<SupplyReportItemDto> reportItems;

    private Double finalPrice;

    private Double finalWeight;

}
