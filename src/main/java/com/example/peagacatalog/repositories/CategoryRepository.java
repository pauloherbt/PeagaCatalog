package com.example.peagacatalog.repositories;

import com.example.peagacatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
