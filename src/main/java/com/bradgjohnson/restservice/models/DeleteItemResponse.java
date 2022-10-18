package com.bradgjohnson.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteItemResponse {
    @JsonProperty("Success")
    private boolean success;
}
