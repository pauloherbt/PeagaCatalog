package com.example.peagacatalog.services.validation;

import com.example.peagacatalog.dto.validations.UserInsertDTO;
import com.example.peagacatalog.repositories.UserRepository;
import com.example.peagacatalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertDTOValid,UserInsertDTO> {
    private UserRepository userRepository;

    public UserInsertValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserInsertDTOValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> errors = new ArrayList<>();
        if(userRepository.findByEmail(userInsertDTO.getEmail()).isPresent()) {
            errors.add(new FieldMessage("email","email already exists"));
        }
        for (FieldMessage error : errors) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage()).addPropertyNode(error.getField()).addConstraintViolation();
        }
        return errors.isEmpty();
    }
}
