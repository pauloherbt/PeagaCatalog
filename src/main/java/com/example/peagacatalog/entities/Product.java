package com.example.peagacatalog.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    @ManyToMany
    @JoinTable(name = "tb_ProductCategory"
            , joinColumns = @JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // VER DIFERENÇA NO POSTEGRESQL
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;
    public Product() {
    }

    public Product(Long id, String name, String description, Double price, String imgUrl,Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        addCategory(category);
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> category) {
        this.categories = category;
    }
    public void addCategory(Category category){
        getCategories().add(category);
    }
    /*servem para fazer algo antes de persistir(1º)vez ou atualizar n vezes, nesse caso estamos trabalhando com dados de auditoria
        , registrando os instantes que ocorreram modificações*/
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
    @PreUpdate
    public void preUpdated() {
        this.updatedAt = Instant.now();
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
