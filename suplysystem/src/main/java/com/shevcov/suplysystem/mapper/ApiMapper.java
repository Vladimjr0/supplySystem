package com.shevcov.suplysystem.mapper;

import com.shevcov.suplysystem.dto.ProductRequestDto;
import com.shevcov.suplysystem.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    Product productRequestDtoToProduct(ProductRequestDto productRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateProductFromDto(ProductRequestDto productRequestDto, @MappingTarget Product product);

}
