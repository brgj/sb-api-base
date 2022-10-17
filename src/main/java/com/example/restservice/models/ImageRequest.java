package main.java.com.example.restservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImageRequest {
    @JsonProperty("numUrls")
    public Integer numUrls;

    @JsonProperty("urls")
    public List<String> urls;
}
