package com.bradgjohnson.restservice.repositories;

import com.bradgjohnson.restservice.models.ItemEntity;

import java.util.Optional;

public interface ItemRepository {
    ItemEntity storeItem(ItemEntity item);
    Optional<ItemEntity> getItem(String id);
    boolean deleteItem(String id);
}
