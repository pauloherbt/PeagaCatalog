package com.example.peagacatalog.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // VER DIFERENÇA NO POSTEGRESQL
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;
    public Category() {
    }

    public Category(Long id, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    public Instant getCreatedAt() {
        return createdAt;
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
    public int hashCode() {
        return Objects.hash(id);
    }
}
