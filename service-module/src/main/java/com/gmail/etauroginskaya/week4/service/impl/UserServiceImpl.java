package com.gmail.etauroginskaya.week4.service.impl;

import com.gmail.etauroginskaya.week4.repository.UserRepository;
import com.gmail.etauroginskaya.week4.repository.model.User;
import com.gmail.etauroginskaya.week4.service.UserService;
import com.gmail.etauroginskaya.week4.service.converter.UserConverter;
import com.gmail.etauroginskaya.week4.service.exception.ServiceException;
import com.gmail.etauroginskaya.week4.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getUsers(connection);
                List<UserDTO> dtos = users.stream()
                        .map(userConverter::toDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return dtos;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Coming transaction Failed! Check output console.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Connection Failed! Check output console.");
        }
    }

    @Override
    public void add(UserDTO user) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User userForSave = userConverter.fromDTO(user);
                userRepository.addUser(connection, userForSave);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Coming transaction Failed! Check output console.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Connection Failed! Check output console.");
        }
    }

    @Override
    public UserDTO findByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByUsername(connection, username);
                UserDTO findUser = userConverter.toDTO(user);
                connection.commit();
                return findUser;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Coming transaction Failed! Check output console.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Connection Failed! Check output console.");
        }
    }
}
