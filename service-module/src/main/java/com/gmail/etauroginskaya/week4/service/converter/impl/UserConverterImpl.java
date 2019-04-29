package com.gmail.etauroginskaya.week4.service.converter.impl;

import com.gmail.etauroginskaya.week4.repository.model.Role;
import com.gmail.etauroginskaya.week4.repository.model.User;
import com.gmail.etauroginskaya.week4.service.converter.RoleConverter;
import com.gmail.etauroginskaya.week4.service.converter.UserConverter;
import com.gmail.etauroginskaya.week4.service.model.RoleDTO;
import com.gmail.etauroginskaya.week4.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            RoleDTO roleDTO = roleConverter.toDTO(user.getRole());
            userDTO.setRoleDTO(roleDTO);
        }
        return userDTO;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        Role role = roleConverter.fromDTO(userDTO.getRoleDTO());
        user.setRole(role);
        return user;
    }
}
