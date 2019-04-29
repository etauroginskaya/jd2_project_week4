package com.gmail.etauroginskaya.week4.service.converter;

import com.gmail.etauroginskaya.week4.service.model.ItemDTO;
import com.gmail.etauroginskaya.week4.repository.model.Item;

public interface ItemConverter {

    ItemDTO toDTO(Item item);

    Item fromDTO(ItemDTO itemDTO);
}
