package com.example.peagacatalog.services;

import com.example.peagacatalog.entities.Category;
import com.example.peagacatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
