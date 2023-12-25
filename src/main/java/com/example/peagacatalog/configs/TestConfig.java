package com.example.peagacatalog.configs;

import com.example.peagacatalog.entities.Product;
import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public void run(String... args)  {
        Product p1 = new Product(null,"Computer","a basic pc",1800.5,"url//",categoryRepository.findById(2L).orElseThrow());
        Product p2 = new Product(null,"Balancer","a basic pc",1800.5,"url//",categoryRepository.findById(1L).orElseThrow());
        p1.addCategory(categoryRepository.findById(1L).orElseThrow());
        p1.addCategory(categoryRepository.findById(3L).orElseThrow());
        p1.addCategory(categoryRepository.findById(4L).orElseThrow());
        p1.addCategory(categoryRepository.findById(2L).orElseThrow());
        productRepository.saveAll(List.of(p1,p2));
    }
}
