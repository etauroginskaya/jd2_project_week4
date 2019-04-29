package com.gmail.etauroginskaya.week4.repository;

import com.gmail.etauroginskaya.week4.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository extends ConnectionRepository {

    List<Item> getItems(Connection connection);
}
