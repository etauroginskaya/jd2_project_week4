package com.gmail.etauroginskaya.week4.repository.impl;

import com.gmail.etauroginskaya.week4.repository.ConnectionRepository;
import com.gmail.etauroginskaya.week4.repository.exception.DatabaseConnectionException;
import com.gmail.etauroginskaya.week4.repository.exception.DatabaseQueryException;
import com.gmail.etauroginskaya.week4.repository.exception.DocumentReadException;
import com.gmail.etauroginskaya.week4.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AbstractRepository implements ConnectionRepository {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);
    private static final String CONNECTION_ERROR_MESSAGE = "Connection Failed! Check output console.";
    private final DatabaseProperties databaseProperties;

    @Autowired
    public AbstractRepository(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            Class.forName(databaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", databaseProperties.getUsername());
            properties.setProperty("password", databaseProperties.getPassword());
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "cp1251");
            return DriverManager.getConnection(databaseProperties.getUrl(), properties);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @PostConstruct
    private void createDatabaseTables() {
        String scriptName = getClass().getResource("/" + databaseProperties.getScript()).getPath();
        List<String> listQueries = getListQueries(scriptName);
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String query : listQueries) {
                    statement.addBatch(query);
                }
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQL query Failed! Check output console.", e);
                throw new DatabaseQueryException("SQL query Failed! Check output console.");
            }
        } catch (SQLException e) {
            logger.error(CONNECTION_ERROR_MESSAGE, e);
            throw new DatabaseConnectionException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    List<String> getListQueries(String fileName) {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
                String line;
                List<String> listQueries = new ArrayList<>();
                while ((line = in.readLine()) != null) {
                    listQueries.add(line);
                }
                return listQueries;
            } catch (FileNotFoundException e) {
                logger.error("Script to create database tables not found!", e);
                throw new FileNotFoundException("Script to create database tables not found!");
            }
        } catch (IOException e) {
            logger.error("Reading SQL-file Failed! Check output console.", e);
            throw new DocumentReadException("Reading SQL-file Failed! Check output console.", e);
        }
    }
}
