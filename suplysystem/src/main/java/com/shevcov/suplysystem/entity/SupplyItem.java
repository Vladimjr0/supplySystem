package com.shevcov.suplysystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SupplyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    @JsonBackReference
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double weight;

    private double price;

}
