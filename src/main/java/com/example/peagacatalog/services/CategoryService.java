package com.example.peagacatalog.services;

import com.example.peagacatalog.dto.CategoryDTO;
import com.example.peagacatalog.entities.Category;
import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<CategoryDTO> findAll() {
        List<CategoryDTO>  dtoList = new ArrayList<>();
        categoryRepository.findAll().forEach((c)->dtoList.add(new CategoryDTO(c)));
        //List<CategoryDTO>  dtoList= categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList()); //(c)-> new CategoryDTO(c)
        return dtoList;
    }
    public CategoryDTO create(Category category){
        return new CategoryDTO(categoryRepository.save(category));
    }
    public CategoryDTO findById(Long id){
        return new CategoryDTO(categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" Category not found by id: "+id)));
    }
   /* public void delete(Category){
        categoryRepository.delete();
    }*/
}
