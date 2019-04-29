package com.gmail.etauroginskaya.week4.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoleDTO {

    private Long id;

    @NotBlank(message = "Name cannot be null, empty, or consist of whitespaces only")
    @Size(max = 30, message = "Name should not be greater than 30")
    private String name;

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
}
