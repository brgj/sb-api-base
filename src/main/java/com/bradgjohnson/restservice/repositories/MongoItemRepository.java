package com.bradgjohnson.restservice.repositories;

import com.bradgjohnson.restservice.models.ItemEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Repository("mongo_impl")
public class MongoItemRepository implements ItemRepository {
    private final String COLLECTION_NAME = "items";

    private final MongoCollection<ItemEntity> collection;

    public MongoItemRepository(@Autowired MongoDatabase mongoDatabase) {
        // Initialize DB if it doesn't already exist
        var exists = new AtomicBoolean(false);
        mongoDatabase.listCollectionNames().forEach((Consumer<? super String>) coll -> exists.set(coll.equals(COLLECTION_NAME) ^ exists.get()));
        if (!exists.get()) {
            mongoDatabase.createCollection(COLLECTION_NAME);
        }

        collection = mongoDatabase.getCollection(COLLECTION_NAME, ItemEntity.class);
    }

    @Override
    public ItemEntity storeItem(ItemEntity item) {
        collection.insertOne(item);

        return item;
    }

    @Override
    public Optional<ItemEntity> getItem(String id) {
        Document searchDoc = new Document();
        searchDoc.put("_id", new ObjectId(id));
        var items = collection.find(searchDoc);

        return Optional.ofNullable(items.first());
    }

    @Override
    public boolean deleteItem(String id) {
        Document searchDoc = new Document();
        searchDoc.put("_id", new ObjectId(id));
        var result = collection.deleteOne(searchDoc);
        return result.getDeletedCount() > 0;
    }
}
