package com.bradgjohnson.restservice.repositories;

import com.bradgjohnson.restservice.models.LImg;

import java.util.List;

public interface ImageRepository {
    List<LImg> getImages(List<String> urls);
    LImg getImage(String url);
}
