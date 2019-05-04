package com.gmail.etauroginskaya.week4.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Username cannot be null, empty, or consist of whitespaces only")
    @Size(max = 70, message = "Name should not be greater than 70")
    private String username;

    @NotBlank(message = "Password cannot be null, empty, or consist of whitespaces only")
    @Size(max = 60, message = "Name should not be greater than 60")
    private String password;

    private RoleDTO role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
