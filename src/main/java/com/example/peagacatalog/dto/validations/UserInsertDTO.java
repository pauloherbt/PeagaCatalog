package com.example.peagacatalog.dto.validations;

import com.example.peagacatalog.dto.UserDTO;
import com.example.peagacatalog.services.validation.UserInsertDTOValid;
import jakarta.validation.constraints.NotBlank;

@UserInsertDTOValid
public class UserInsertDTO extends UserDTO {
    @NotBlank
    private String password;

    public UserInsertDTO(String password) {
        super();
        this.password = password;
    }

    public UserInsertDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
