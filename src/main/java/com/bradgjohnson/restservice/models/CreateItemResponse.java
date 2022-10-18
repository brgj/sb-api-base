package com.bradgjohnson.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateItemResponse {
    @JsonProperty("Id")
    private String id;
}
