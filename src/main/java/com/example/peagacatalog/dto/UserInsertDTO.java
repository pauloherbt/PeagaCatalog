package com.example.peagacatalog.dto;

public class UserInsertDTO extends UserDTO{
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
