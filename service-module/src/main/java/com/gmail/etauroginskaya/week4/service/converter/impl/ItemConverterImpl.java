package com.gmail.etauroginskaya.week4.service.converter.impl;

import com.gmail.etauroginskaya.week4.repository.model.Item;
import com.gmail.etauroginskaya.week4.service.converter.ItemConverter;
import com.gmail.etauroginskaya.week4.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setStatus(item.getStatus().name());
        return itemDTO;
    }

    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        //TODO
        return null;
    }
}
