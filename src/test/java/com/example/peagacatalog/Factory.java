package com.example.peagacatalog;

import com.example.peagacatalog.dto.ProductDTO;
import com.example.peagacatalog.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct(){
        return new Product(null,"Teste","TesteDesc",800.0,"teste", Instant.now());
    }
    public static ProductDTO creatProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product,product.getCategories());
    }
    public static ProductDTO createProductDtoWithId(){
        ProductDTO dto = creatProductDTO();
        dto.setId(1L);
        return dto;
    }
}
