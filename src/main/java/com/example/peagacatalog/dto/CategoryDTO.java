package com.example.peagacatalog.dto;

import com.example.peagacatalog.entities.Category;

public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Category category) {
        setId(category.getId());
        setName(category.getName());
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
