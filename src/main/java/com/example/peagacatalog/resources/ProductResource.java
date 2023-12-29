package com.example.peagacatalog.resources;

import com.example.peagacatalog.dto.ProductDTO;
import com.example.peagacatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    @Autowired
    private ProductService productService;
    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(defaultValue = "0") Integer pgNumber
            , @RequestParam(defaultValue = " 10") Integer pgSize
            , @RequestParam(defaultValue = "id") String orderBy
            ,@RequestParam(defaultValue = "ASC") String direction) {
        Pageable pg = PageRequest.of(pgNumber,pgSize, Sort.Direction.valueOf(direction),orderBy);
        return ResponseEntity.ok(productService.findAll(pg));
    }
   @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO){
        ProductDTO dtoCreated = productService.create(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dtoCreated.getId());
        return ResponseEntity.created(uri).body(dtoCreated);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,@RequestBody ProductDTO productDTO){
        ProductDTO dto = productService.update(id,productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dto.getId());
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
