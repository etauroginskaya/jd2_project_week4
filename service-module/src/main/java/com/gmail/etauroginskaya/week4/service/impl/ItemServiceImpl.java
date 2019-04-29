package com.gmail.etauroginskaya.week4.service.impl;

import com.gmail.etauroginskaya.week4.repository.model.Item;
import com.gmail.etauroginskaya.week4.service.ItemService;
import com.gmail.etauroginskaya.week4.service.converter.ItemConverter;
import com.gmail.etauroginskaya.week4.service.exception.ServiceException;
import com.gmail.etauroginskaya.week4.service.model.ItemDTO;
import com.gmail.etauroginskaya.week4.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public List<ItemDTO> getItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.getItems(connection);
                List<ItemDTO> dtos = items.stream()
                        .map(itemConverter::toDTO)
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
}
