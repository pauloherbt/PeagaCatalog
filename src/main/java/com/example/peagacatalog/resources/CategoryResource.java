package com.example.peagacatalog.resources;

import com.example.peagacatalog.dto.CategoryDTO;
import com.example.peagacatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(@RequestParam(defaultValue = "0") Integer pgNumber, @RequestParam(defaultValue = " 10") Integer pgSize, @RequestParam(defaultValue = "id") String orderBy
            ,@RequestParam(defaultValue = "ASC") String direction){
        PageRequest pg = PageRequest.of(pgNumber,pgSize, Sort.Direction.valueOf(direction),orderBy);
        return ResponseEntity.ok(categoryService.findAllPaged(pg));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO dtoCreated = categoryService.create(categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dtoCreated.getId());
        return ResponseEntity.created(uri).body(dtoCreated);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@RequestBody CategoryDTO categoryDTO){
        CategoryDTO dto = categoryService.update(id,categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dto.getId());
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
