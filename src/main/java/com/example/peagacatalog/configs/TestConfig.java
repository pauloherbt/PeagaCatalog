package com.example.peagacatalog.configs;

import com.example.peagacatalog.repositories.CategoryRepository;
import com.example.peagacatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    @Autowired
    public TestConfig(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args)  {
        /*Product p1 = new Product(null,"Computer","a basic pc",1800.5,"url//",categoryRepository.findById(2L).orElseThrow());
        Product p2 = new Product(null,"Balancer","a basic pc",1800.5,"url//",categoryRepository.findById(1L).orElseThrow());
        p1.addCategory(categoryRepository.findById(1L).orElseThrow());
        p1.addCategory(categoryRepository.findById(3L).orElseThrow());
        p1.addCategory(categoryRepository.findById(4L).orElseThrow());
        p1.addCategory(categoryRepository.findById(2L).orElseThrow());
        productRepository.saveAll(List.of(p1,p2));*/
    }
}
