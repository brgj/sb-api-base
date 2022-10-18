package com.bradgjohnson.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ItemModel {
    @JsonProperty("Name")
    String name;
    @JsonProperty("Value")
    String value;

    public static ItemModel from(ItemEntity domainItem) {
        return ItemModel.builder()
                .name(domainItem.name)
                .value(domainItem.value)
                .build();
    }
}
