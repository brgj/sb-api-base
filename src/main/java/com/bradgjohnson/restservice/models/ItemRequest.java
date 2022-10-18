package com.bradgjohnson.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ItemRequest {
    @JsonProperty("Item")
    private ItemModel itemModel;
}
