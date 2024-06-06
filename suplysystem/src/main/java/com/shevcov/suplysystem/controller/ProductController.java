package com.shevcov.suplysystem.controller;

import com.shevcov.suplysystem.dto.ProductRequestDto;
import com.shevcov.suplysystem.entity.Product;
import com.shevcov.suplysystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер для взаимодействия с товарами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Метод для создания товара")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestDto  productRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequestDto));
    }

    @Operation(summary = "Метод для просмотра всего списка товаров")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Метод для удаления товара по Id")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для обновления товара по Id")
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId,
                                              @Valid @RequestBody ProductRequestDto productRequestDto){
        productService.updateProductById(productId, productRequestDto);
        return ResponseEntity.noContent().build();
    }

}
