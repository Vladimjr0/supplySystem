package com.shevcov.suplysystem.service;

import com.shevcov.suplysystem.dto.SupplierRequestDto;
import com.shevcov.suplysystem.entity.Supplier;
import com.shevcov.suplysystem.repository.SupplierRepository;
import com.shevcov.suplysystem.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    private final SupplyRepository supplyRepository;

    @Transactional
    public Supplier createSupplier(SupplierRequestDto supplierRequestDto){

        Supplier supplier = new Supplier();
        supplier.setName(supplierRequestDto.getName());
        return supplierRepository.save(supplier);

    }

    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll(Sort.by("name"));
    }


    @Transactional
    public void deleteSupplierById(Long supplierId){
        if(supplierRepository.existsById(supplierId)){
            supplyRepository.deleteBySupplierId(supplierId);
            supplierRepository.deleteById(supplierId);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Поставщик с таким Id не найден");
        }
    }

    @Transactional
    public void updateSupplierById(Long supplierId, SupplierRequestDto supplierRequestDto ){
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Поставщик с таким Id не найден"));

        supplier.setName(supplierRequestDto.getName());
        supplierRepository.save(supplier);
    }


}
