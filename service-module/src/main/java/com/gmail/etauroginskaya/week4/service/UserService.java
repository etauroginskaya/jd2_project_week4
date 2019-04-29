package com.gmail.etauroginskaya.week4.service;

import com.gmail.etauroginskaya.week4.service.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void add(UserDTO user);

    UserDTO findByUsername(String username);
}
