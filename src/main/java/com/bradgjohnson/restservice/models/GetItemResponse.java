package com.bradgjohnson.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetItemResponse {
    @JsonProperty("Item")
    private ItemModel itemModel;
}
