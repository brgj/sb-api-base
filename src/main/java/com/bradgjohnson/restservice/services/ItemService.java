package com.bradgjohnson.restservice.services;

import com.bradgjohnson.restservice.models.ItemEntity;
import com.bradgjohnson.restservice.models.ItemModel;
import com.bradgjohnson.restservice.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    @Qualifier("cached_impl")
    ItemRepository repositoryImpl;

    public String storeItem(ItemModel itemModel) {
        ItemEntity domainItem = repositoryImpl.storeItem(ItemEntity.from(itemModel));

        return domainItem.getId().toHexString();
    }

    public Optional<ItemEntity> getItem(String itemId) {
        return repositoryImpl.getItem(itemId);
    }

    public boolean deleteItem(String itemId) {
        return repositoryImpl.deleteItem(itemId);
    }
}
