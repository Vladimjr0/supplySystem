package com.shevcov.suplysystem.service;

import com.shevcov.suplysystem.dto.SupplyRequestDto;
import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.entity.Supplier;
import com.shevcov.suplysystem.entity.Supply;
import com.shevcov.suplysystem.entity.SupplyItem;
import com.shevcov.suplysystem.exception.SupplyNotFoundException;
import com.shevcov.suplysystem.repository.ProductRepository;
import com.shevcov.suplysystem.repository.SupplierRepository;
import com.shevcov.suplysystem.repository.SupplyItemRepository;
import com.shevcov.suplysystem.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;

    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;

    private final SupplyItemRepository supplyItemRepository;


    @Transactional
    public Supply createSupply(SupplyRequestDto supplyRequestDto){
        Supplier supplier = supplierRepository.findById(supplyRequestDto.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Поставщик не найден"));

        Supply supply = new Supply();
        supply.setSupplier(supplier);

        supply.setItems(createSupplyItemsBySupply(supply, supplyRequestDto));
        supply.setSupplyDate(LocalDate.now());
        return supplyRepository.save(supply);
    }

    private List<SupplyItem> createSupplyItemsBySupply(Supply supply, SupplyRequestDto supplyRequestDto){
        return supplyRequestDto.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
                    SupplyItem item = new SupplyItem();
                    item.setProduct(product);
                    item.setWeight(itemRequest.getWeight());
                    item.setPrice(itemRequest.getPrice());
                    item.setSupply(supply);
                    return item;
                })
                .collect(Collectors.toList());
    }

    public List<Supply> getSuppliesByDateRange(LocalDate startDate, LocalDate endDate) {
        return supplyRepository.findBySupplyDateBetween(startDate, endDate);
    }

    public List<Supply> getAllSupply(){
        return supplyRepository.findAll();
    }

    public List<Supply> getSupplyBySupplier(Long supplierId){
        return supplyRepository.findBySupplierId(supplierId);
    }

    @Transactional
    public void deleteSupplyById(Long supplyId){
        if(supplyRepository.existsById(supplyId)){
            supplyRepository.deleteById(supplyId);
        }else{
            throw new SupplyNotFoundException("Поставка с таким Id не найдена");
        }
    }

    public Supply getSupplyById(Long supplyId){
        return supplyRepository.findById(supplyId)
                .orElseThrow(()-> new SupplyNotFoundException("Поставка с таким Id не найдена"));
    }

    @Transactional
    public void updateSupply(SupplyRequestDto supplyRequestDto, Long supplyId){
        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(()-> new SupplyNotFoundException("Поставка с таким Id не найдена"));

        supply.setSupplier(supplierRepository.findById(supplyRequestDto.getSupplierId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Поставщик не найден")));

        supplyItemRepository.deleteAllBySupplyId(supplyId);


        supply.setItems(createSupplyItemsBySupply(supply, supplyRequestDto));
        supplyRepository.save(supply);

    }
}
