package com.gmail.etauroginskaya.week4.repository.impl;

import com.gmail.etauroginskaya.week4.repository.ItemRepository;
import com.gmail.etauroginskaya.week4.repository.exception.DatabaseQueryException;
import com.gmail.etauroginskaya.week4.repository.model.Item;
import com.gmail.etauroginskaya.week4.repository.model.ItemStatusEnum;
import com.gmail.etauroginskaya.week4.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends AbstractRepository implements ItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Autowired
    public ItemRepositoryImpl(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public List<Item> getItems(Connection connection) {
        String sql = "SELECT * FROM item";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(getItem(rs));
                }
                return items;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException("SQL query Failed! Check output console.");
        }
    }

    Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setStatus(ItemStatusEnum.valueOf(resultSet.getString("status")));
        return item;
    }
}
