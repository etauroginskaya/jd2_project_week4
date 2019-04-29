package com.gmail.etauroginskaya.week4.service.converter.impl;

import com.gmail.etauroginskaya.week4.repository.model.Role;
import com.gmail.etauroginskaya.week4.service.converter.RoleConverter;
import com.gmail.etauroginskaya.week4.service.model.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public Role fromDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }
}
