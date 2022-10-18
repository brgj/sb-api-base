package com.bradgjohnson.restservice.repositories;


import com.bradgjohnson.restservice.cache.LRUCache;
import com.bradgjohnson.restservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cached_impl")
public class CachedItemRepository implements ItemRepository {
    LRUCache<String, ItemEntity> cache;

    @Autowired
    @Qualifier("mongo_impl")
    ItemRepository repositoryImpl;

    public CachedItemRepository(@Value("${cache_size}") long maxSize) {
        cache = new LRUCache<>(maxSize);
    }

    @Override
    public ItemEntity storeItem(ItemEntity itemEntity) {
        var resultItem = repositoryImpl.storeItem(itemEntity);

        cache.put(resultItem.getId().toHexString(), itemEntity);

        return resultItem;
    }

    @Override
    public Optional<ItemEntity> getItem(String id) {
        if (!cache.contains(id)) {
            Optional<ItemEntity> domainItem = repositoryImpl.getItem(id);
            domainItem.ifPresent(di -> cache.put(di.getId().toHexString(), di));
            return domainItem;
        }
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public boolean deleteItem(String id) {
        if (cache.contains(id)) {
            cache.remove(id);
        }
        return repositoryImpl.deleteItem(id);
    }
}
