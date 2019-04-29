package com.gmail.etauroginskaya.week4.repository.impl;

import com.gmail.etauroginskaya.week4.repository.UserRepository;
import com.gmail.etauroginskaya.week4.repository.exception.DatabaseQueryException;
import com.gmail.etauroginskaya.week4.repository.model.Role;
import com.gmail.etauroginskaya.week4.repository.model.User;
import com.gmail.etauroginskaya.week4.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String QUERY_ERROR_MESSAGE = "SQL query Failed! Check output console.";

    @Autowired
    public UserRepositoryImpl(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public List<User> getUsers(Connection connection) {
        String sql = "SELECT u.id, u.username, u.role_id, u.password, r.name as name_role  FROM user u JOIN role r ON u.role_id = r.id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(getUser(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public void addUser(Connection connection, User user) {
        String sql = "INSERT INTO user(username, password) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Creating user failed (query " + sql + "), no row affected.");
                throw new DatabaseQueryException("Creating user failed (query " + sql + "), no row affected.");
            }
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (!rs.next()) {
                    logger.error("Creating user failed (query " + sql + "), no ID obtained.");
                    throw new DatabaseQueryException("Creating user failed (query " + sql + "), no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public User getUserByUsername(Connection connection, String username) {
        String sql = "SELECT u.id, u.username, u.role_id, u.password, r.name as name_role FROM user u " +
                "JOIN role r ON u.role_id = r.id WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        Role role = new Role();
        role.setId(resultSet.getLong("role_id"));
        role.setName(resultSet.getString("name_role"));
        user.setRole(role);
        return user;
    }
}
