package com.bradgjohnson.restservice.models;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    ObjectId id;
    @BsonProperty("name")
    String name;
    @BsonProperty("value")
    String value;

    public static ItemEntity from(ItemModel itemModel) {
        return ItemEntity.builder()
                .name(itemModel.name)
                .value(itemModel.value)
                .build();
    }
}
