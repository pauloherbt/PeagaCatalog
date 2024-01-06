package com.example.peagacatalog.services;

import com.example.peagacatalog.dto.CategoryDTO;
import com.example.peagacatalog.entities.Category;
import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        List<CategoryDTO>  dtoList = new ArrayList<>();
        categoryRepository.findAll().forEach((c)->dtoList.add(new CategoryDTO(c)));
        //List<CategoryDTO>  dtoList= categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList()); //(c)-> new CategoryDTO(c)
        return dtoList;
    }
    public CategoryDTO create(CategoryDTO category){
        return new CategoryDTO(categoryRepository.save(new Category(null,category.getName())));
    }
    public CategoryDTO findById(Long id){
        return new CategoryDTO(categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" Category not found by id: "+id)));
    }
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO){
        try{
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(categoryDTO.getName());
            return new CategoryDTO(categoryRepository.save(entity));
        }catch (jakarta.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Resource not found by id: "+id);
        }
    }
   public void deleteById(Long id){
        if(categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Resource not found id: "+id);
    }

    public Page<CategoryDTO> findAllPaged(Pageable pg) {
        return categoryRepository.findAll(pg).map(CategoryDTO::new);
    }
}
