package main.java.com.example.restservice.models;

import com.example.restservice.cache.ItemWithSize;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private ImageSource source;
        private Integer sizeBytes;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder source(ImageSource source) {
            this.source = source;
            return this;
        }

        public Builder sizeBytes(Integer sizeBytes) {
            this.sizeBytes = sizeBytes;
            return this;
        }

        public LImg build() {
            return new LImg(url, source, sizeBytes);
        }
    }
}
