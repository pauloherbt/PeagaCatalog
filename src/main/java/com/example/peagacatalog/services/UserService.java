package com.example.peagacatalog.services;

import com.example.peagacatalog.dto.UserDTO;
import com.example.peagacatalog.entities.User;
import com.example.peagacatalog.repositories.RoleRepository;
import com.example.peagacatalog.repositories.UserRepository;
import com.example.peagacatalog.services.exceptions.DbException;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Page<UserDTO> findAll(Pageable pg) {
        return userRepository.findAll(pg).map(UserDTO::new);
    }
    @Transactional
    public UserDTO create(UserDTO userDTO){
        User entity = new User();
        copyDTOToEntity(entity,userDTO);
        return new UserDTO(userRepository.save(entity));
    }
    @Transactional
   public UserDTO findById(Long id){
        User entity = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" User not found by id: "+id));
        return new UserDTO(entity);
    }
    @Transactional
    public UserDTO update(Long id, UserDTO userDTO){
        try{
            User entity = userRepository.getReferenceById(id);
            copyDTOToEntity(entity,userDTO);
            return new UserDTO(userRepository.save(entity));
        }catch (jakarta.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Resource not found by id: "+id);
        }
    }
    @Transactional
    public void deleteById(Long id){
        if(!userRepository.existsById(id))
            throw new EntityNotFoundException("Resource not found by id: "+id);
        try{
            userRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DbException("integrity violation exception");
        }
    }
    private void copyDTOToEntity(User entity, UserDTO userDTO){
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setEmail(userDTO.getEmail());
        entity.getRoles().addAll(userDTO.getRoles()
                .stream().map(x->roleRepository.getReferenceById(x.getId()))
                .collect(Collectors.toSet()));
    }

}
