package com.example.peagacatalog.services.validation;

import com.example.peagacatalog.dto.validations.UserUpdateDTO;
import com.example.peagacatalog.entities.User;
import com.example.peagacatalog.repositories.UserRepository;
import com.example.peagacatalog.resources.exceptions.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateDTOValid, UserUpdateDTO> {
    private UserRepository userRepository;
    private HttpServletRequest httpServletRequest;
    @Autowired
    public UserUpdateValidator(UserRepository userRepository, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void initialize(UserUpdateDTOValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDTO userUpdateDTO, ConstraintValidatorContext constraintValidatorContext) {
        User userFound;
        List<FieldMessage> errors = new ArrayList<>();
        if((userFound= userRepository.findByEmail(userUpdateDTO.getEmail()))!=null){
            /*String path[] = httpServletRequest.getServletPath().split("/");
            String resource = path[path.length-1];
            if(!resource.equals(String.valueOf(userFound.getId()))){*/
            //bizzaro isso aqui
            var map = (Map<String,String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if(!map.get("id").equals(userFound.getId().toString()))
                errors.add(new FieldMessage("email", "email already registered"));
        }
        for (FieldMessage error : errors) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage()).addPropertyNode(error.getField()).addConstraintViolation();
        }
        return errors.isEmpty();
    }
}
