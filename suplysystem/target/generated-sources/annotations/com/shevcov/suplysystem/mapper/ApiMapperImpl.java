package com.shevcov.suplysystem.mapper;

import com.shevcov.suplysystem.dto.ProductRequestDto;
import com.shevcov.suplysystem.entity.Product;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-07T11:19:08+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class ApiMapperImpl implements ApiMapper {

    @Override
    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
        if ( productRequestDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( productRequestDto.getName() );
        product.setType( productRequestDto.getType() );

        return product;
    }

    @Override
    public void updateProductFromDto(ProductRequestDto productRequestDto, Product product) {
        if ( productRequestDto == null ) {
            return;
        }

        product.setName( productRequestDto.getName() );
        product.setType( productRequestDto.getType() );
    }
}
