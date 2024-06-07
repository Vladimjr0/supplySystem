package com.shevcov.suplysystem.service;

import com.shevcov.suplysystem.dto.ProductRequestDto;
import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.mapper.ApiMapper;
import com.shevcov.suplysystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(ProductRequestDto productRequestDto){
        return productRepository.save(ApiMapper.INSTANCE.productRequestDtoToProduct(productRequestDto));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    public void deleteProductById(Long productId){
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар с таким Id не найден");
        }
    }

    @Transactional
    public void updateProductById(Long productId, ProductRequestDto productRequestDto){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар с таком Id не найден"));
        ApiMapper.INSTANCE.updateProductFromDto(productRequestDto, product);

        productRepository.save(product);

    }
}
