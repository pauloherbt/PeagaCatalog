package com.example.peagacatalog.services;

import com.example.peagacatalog.dto.ProductDTO;
import com.example.peagacatalog.entities.Category;
import com.example.peagacatalog.entities.Product;
import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.Factory;
import com.example.peagacatalog.repositories.ProductRepository;
import com.example.peagacatalog.services.exceptions.DbException;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private Long existingId;
    private Long nonExistingId,nonExistingCategoryId;
    private Long dependentId;
    private Product product;
    private Category category;
    private Page<Product> pg;
    @BeforeEach()
    void setup() {
        existingId = 1L; nonExistingId = 1000L; nonExistingCategoryId = 25L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = new Category(1L,"Eletronics");
        pg = new PageImpl<>(List.of(product));

        when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        when(categoryRepository.getReferenceById(nonExistingCategoryId)).thenThrow(jakarta.persistence.EntityNotFoundException.class);

        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.getReferenceById(existingId)).thenReturn(product);

        when(productRepository.existsById(existingId)).thenReturn(true);
        when(productRepository.existsById(nonExistingId)).thenReturn(false);
    }

    @Test
    void deleteShouldDoNothingWhenExistingId() {
        doNothing().when(productRepository).deleteById(existingId);
        assertDoesNotThrow(() -> productService.deleteById(existingId));
        verify(productRepository, times(1)).existsById(existingId);
        verify(productRepository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowEntityNotFoundExceptionWhenNonExistingId() {

        assertThrows(EntityNotFoundException.class, () -> productService.deleteById(nonExistingId));
        verify(productRepository, times(1)).existsById(nonExistingId);
        verify(productRepository, never()).deleteById(nonExistingId);
    }

    @Test
    void deleteShouldThrowDbExceptionWhenIdViolateIntegrity() {
        doThrow(DbException.class).when(productRepository).deleteById(dependentId);
        assertThrows(DbException.class, () -> productRepository.deleteById(dependentId));
        verify(productRepository).deleteById(dependentId);
    }

    @Test
    void findAllShouldReturnAPageWhenAPageIsPassed() {
        when(productRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(pg);
        Pageable pageable = PageRequest.of(0,1);
        assertNotNull(productService.findAll(pageable));
        verify(productRepository).findAll(pageable);
    }
    @Test
    void findByIdShouldReturnAProductDtoWhenIdExisting(){
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        ProductDTO productDTO = productService.findById(existingId);
        assertNotNull(productDTO);
    }
    @Test
    void findByIdShouldThrowEntityNotFoundExceptionWhenNonExistingId(){
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->productService.findById(nonExistingId));
    }
    @Test
    void updateShouldReturnProductDtoWhenExistingId(){
        ProductDTO productDTO = Factory.creatProductDTO();
        ProductDTO productDTUpdated = productService.update(existingId,productDTO);
        assertNotNull(productDTUpdated);
        assertEquals(productDTO.getId(),productDTUpdated.getId());
        assertEquals(productDTO.getDescription(),productDTUpdated.getDescription());
        assertEquals(productDTO.getImgUrl(),productDTUpdated.getImgUrl());
        assertEquals(productDTO.getPrice(),productDTUpdated.getPrice());
        assertEquals(productDTO.getCategories().size(),productDTUpdated.getCategories().size());
    }
    @Test
    void updateShouldThrowEntityNotFoundExceptionWhenNonExistingId(){
        when(productRepository.getReferenceById(nonExistingId)).thenThrow(jakarta.persistence.EntityNotFoundException.class);
        ProductDTO dto=Factory.creatProductDTO();
        assertThrows(EntityNotFoundException.class,()->productService.update(nonExistingId,dto));
        verify(productRepository,never()).save(ArgumentMatchers.any());
    }
}
