package com.example.peagacatalog.resources;

import com.example.peagacatalog.Factory;
import com.example.peagacatalog.dto.ProductDTO;
import com.example.peagacatalog.services.ProductService;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductResource.class)//usa mockbean
@AutoConfigureMockMvc(addFilters = false)
class ProductResourceTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    private Page<ProductDTO> pgDto;
    private ProductDTO productDTO;
    private Long existingId,nonExistingId;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setup(){
        existingId=1L;
        nonExistingId=99L;
        Pageable pg = PageRequest.ofSize(1);
        productDTO = Factory.creatProductDTO();
        pgDto = new PageImpl<>(List.of(productDTO),pg,1L);
        objectMapper = new ObjectMapper();

    }
    @Test
    void findAllShouldReturnAPageOfProductDto() throws Exception{
        when(productService.findAll(any(Pageable.class))).thenReturn(pgDto);
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }
    @Test
    void findByIdShouldReturnAProductDtoWhenIdExists() throws Exception {
        when(productService.findById(existingId)).thenReturn(Factory.creatProductDTO());
        mockMvc.perform(get("/products/{id}",existingId))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(existingId));
    }
    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        when(productService.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/products/{id}",nonExistingId))
                .andExpect(status().isNotFound());
    }
    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        doNothing().when(productService).deleteById(existingId);
        mockMvc.perform(delete("/products/{id}",existingId)).andExpect(status().isNoContent());
    }
    @Test
    void deleteShouldReturnEntityNotFoundWhenIdDoesNotExist() throws Exception {
        doThrow(EntityNotFoundException.class).when(productService).deleteById(nonExistingId);
        mockMvc.perform(delete("/products/{id}",nonExistingId)).andExpect(status().isNotFound());
    }
    @Test
    void updateShouldReturnEntityNotFoundWhenIdDoesNotExist() throws Exception {
        when(productService.update(eq(nonExistingId),any(ProductDTO.class))).thenThrow(EntityNotFoundException.class);
        String jsonProductDto = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(put("/products/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON).content(jsonProductDto))
                .andExpect(status().isNotFound()).andExpect(header().doesNotExist("Location"));
    }
    @Test
    void updateShouldReturnProductDtoWhenIdExists() throws Exception {
        when(productService.update(eq(existingId),any(ProductDTO.class))).thenReturn(productDTO);
        String jsonProductDto = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(put("/products/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonProductDto))
                .andExpect(status().isOk()).andExpect(header().exists("Location"));
    }
    @Test
    void createShouldReturnCreatedAndAProductDTO() throws Exception {
        when(productService.create(any())).thenReturn(productDTO);
        mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(header().exists("Location")).andExpect(jsonPath("$.id").exists());
    }
}
