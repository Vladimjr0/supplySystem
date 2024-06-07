package com.shevcov.suplysystem.repository;
import com.shevcov.suplysystem.entity.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {

    void deleteAllBySupplyId(Long supplyId);


}
