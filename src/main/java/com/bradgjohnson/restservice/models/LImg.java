package com.bradgjohnson.restservice.models;

import com.bradgjohnson.restservice.cache.ItemWithSize;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class LImg implements ItemWithSize {
    private final String url;
    private final ImageSource source;
    private final Integer sizeBytes;

    private LImg(String url, ImageSource source, Integer sizeBytes) {
        this.url = url;
        this.source = source;
        this.sizeBytes = sizeBytes;
    }

    @JsonProperty("Url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("Source")
    public ImageSource getSource() {
        return source;
    }

    @JsonProperty("SizeInBytes")
    public Integer getSizeBytes() {
        return sizeBytes;
    }
}
