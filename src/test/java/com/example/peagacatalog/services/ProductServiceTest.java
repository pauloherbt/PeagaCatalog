package com.example.peagacatalog.services;

import com.example.peagacatalog.repositories.ProductRepository;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private Long existingId;
    private Long nonExistingId;
    private Long totalProducts;

    @BeforeEach()
    void setup(){
        existingId = 1L;
        nonExistingId = 1000L;
        totalProducts= 25L;
        doReturn(true).when(productRepository).existsById(existingId);
        doThrow(EntityNotFoundException.class).when(productRepository).existsById(nonExistingId);
        doNothing().when(productRepository).deleteById(existingId);
    }
    @Test
    void deleteShouldDoNothingWhenExistingId(){
        assertDoesNotThrow(() -> productService.deleteById(existingId));
        verify(productRepository,times(1)).existsById(existingId);
        verify(productRepository,times(1)).deleteById(existingId);
    }
    @Test
    void deleteShouldThrowEntityNotFoundExceptionWhenNonExistingId(){
        assertThrows(EntityNotFoundException.class,() -> productService.deleteById(nonExistingId));
        verify(productRepository,times(1)).existsById(nonExistingId);
        verify(productRepository,never()).deleteById(nonExistingId);
    }
}
