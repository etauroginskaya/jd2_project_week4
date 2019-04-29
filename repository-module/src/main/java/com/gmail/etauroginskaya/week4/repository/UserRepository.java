package com.gmail.etauroginskaya.week4.repository;

import com.gmail.etauroginskaya.week4.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ConnectionRepository {

    List<User> getUsers(Connection connection);

    void addUser(Connection connection, User user);

    User getUserByUsername(Connection connection, String username);
}
