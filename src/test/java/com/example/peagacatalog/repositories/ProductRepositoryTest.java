package com.example.peagacatalog.repositories;

import com.example.peagacatalog.Factory;
import com.example.peagacatalog.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {
    private Long existingId;
    private Long nonExistingId;
    private Long totalProducts;
    private ProductRepository productRepository;
    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @BeforeEach
    void setup(){
        existingId = 1L;
        nonExistingId = 1000L;
        totalProducts= 25L;
    }
    @Test
    void deleteShouldDeleteProductWhenValidId(){
        productRepository.deleteById(existingId);
        assertTrue(productRepository.findById(existingId).isEmpty());
    }
    @Test
    void findByIdShouldReturnAEmptyOptionalProductWhenInValidId(){
        Optional<Product> product = productRepository.findById(nonExistingId);
        assertTrue(product.isEmpty());
    }

    @Test
    void deleteShouldNotDeleteProductWhenInValidId(){
        productRepository.deleteById(nonExistingId);
        assertEquals(totalProducts,productRepository.count());
    }
    @Test
    void findByIdShouldReturnAOptionalProductWhenValidId(){
        Optional<Product> product = productRepository.findById(existingId);
        assertFalse(product.isEmpty());
    }
    @Test
    void createShouldCreateNewProductWhenIdIsNull(){
        Product product= new Product(null,"Teste","Teste",5.0,"teste", Instant.now());
        product = productRepository.save(product);
        assertEquals(totalProducts+1,product.getId());
        assertEquals(totalProducts+1,productRepository.count());
    }
    @Test
    void findAllShouldReturnAllProducts(){
        assertEquals(totalProducts,productRepository.findAll().size());
    }
}
