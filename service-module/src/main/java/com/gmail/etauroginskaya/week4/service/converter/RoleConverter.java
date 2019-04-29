package com.gmail.etauroginskaya.week4.service.converter;

import com.gmail.etauroginskaya.week4.repository.model.Role;
import com.gmail.etauroginskaya.week4.service.model.RoleDTO;

public interface RoleConverter {

    RoleDTO toDTO(Role role);

    Role fromDTO(RoleDTO roleDTO);
}
