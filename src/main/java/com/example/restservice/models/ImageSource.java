package main.java.com.example.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ImageSource {
    @JsonProperty("IN_CACHE") IN_CACHE,
    @JsonProperty("DOWNLOADED") DOWNLOADED
}
