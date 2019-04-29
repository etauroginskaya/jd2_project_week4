package com.gmail.etauroginskaya.week4.service.converter;

import com.gmail.etauroginskaya.week4.repository.model.User;
import com.gmail.etauroginskaya.week4.service.model.UserDTO;

public interface UserConverter {

    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
