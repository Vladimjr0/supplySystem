package com.shevcov.suplysystem.repository;

import com.shevcov.suplysystem.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    List<Supply> findBySupplyDateBetween(LocalDate startDate, LocalDate endDate);

    List<Supply> findBySupplierId(Long supplierId);

    void deleteBySupplierId(Long supplierID);

}
