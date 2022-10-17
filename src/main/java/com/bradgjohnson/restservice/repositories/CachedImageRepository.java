package com.bradgjohnson.restservice.repositories;

import com.bradgjohnson.restservice.cache.LRUCache;
import com.bradgjohnson.restservice.models.ImageSource;
import com.bradgjohnson.restservice.models.LImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Caching Wrapper for an Image Repository implementation
 */
@Repository("cached_impl")
public class CachedImageRepository implements ImageRepository {
    LRUCache<String, LImg> cache;

    @Autowired
    @Qualifier("http_impl")
    ImageRepository repositoryImpl;

    public CachedImageRepository(@Value("${cache_size_in_bytes}") long maxSize) {
        cache = new LRUCache<>(maxSize);
    }

    @Override
    public LImg getImage(String url) {
        if (!cache.contains(url)) {
            LImg img = repositoryImpl.getImage(url);
            cache.put(img.getUrl(), img);
            return img;
        }
        return LImg.builder()
                .sizeBytes(cache.get(url).getSizeBytes())
                .url(url)
                .source(ImageSource.IN_CACHE)
                .build();
    }

    @Override
    public List<LImg> getImages(List<String> urls) {
        List<LImg> result = new ArrayList<>();
        for(String url : urls) {
            result.add(getImage(url));
        }
        return result;
    }
}
