package com.example.peagacatalog.resources;

import com.example.peagacatalog.dto.UserDTO;
import com.example.peagacatalog.dto.UserInsertDTO;
import com.example.peagacatalog.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Page<UserDTO>> findAll(Pageable page) {
        return ResponseEntity.ok(userService.findAll(page));
    }
   @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserInsertDTO userDTO){
        UserDTO dtoCreated = userService.create(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dtoCreated.getId());
        return ResponseEntity.created(uri).body(dtoCreated);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody UserDTO userDTO){
        UserDTO dto = userService.update(id,userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(dto.getId());
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
