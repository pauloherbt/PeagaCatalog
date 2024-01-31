package com.example.peagacatalog.services;

import com.example.peagacatalog.dto.ProductDTO;
import com.example.peagacatalog.entities.Product;
import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.repositories.ProductRepository;
import com.example.peagacatalog.services.exceptions.DbException;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Page<ProductDTO> findAll(Pageable pg) {
        return productRepository.findAll(pg).map(x->new ProductDTO(x, x.getCategories()));
    }
    @Transactional
    public ProductDTO create(ProductDTO productDTO){
        Product entity = new Product();
        copyDTOToEntity(entity,productDTO);
        return new ProductDTO(productRepository.save(entity),entity.getCategories());
    }
    @Transactional
   public ProductDTO findById(Long id){
        Product entity = productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" Product not found by id: "+id));
        return new ProductDTO(entity,entity.getCategories());
    }
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        try{
            Product entity = productRepository.getReferenceById(id);
            copyDTOToEntity(entity,productDTO);
            return new ProductDTO(productRepository.save(entity),entity.getCategories());
        }catch (jakarta.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Resource not found by id: "+id);
        }
    }
    @Transactional
    public void deleteById(Long id){
        if(!productRepository.existsById(id))
            throw new EntityNotFoundException("Resource not found by id: "+id);
        try{
            productRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DbException("integrity violation exception");
        }
    }
    private void copyDTOToEntity(Product entity,ProductDTO productDTO){
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
        if(entity.getDate()==null)
            entity.setDate(Instant.now());
        entity.getCategories().addAll(productDTO.getCategories().stream().map(dto -> categoryRepository.getReferenceById(dto.getId())).toList());
    }

}
