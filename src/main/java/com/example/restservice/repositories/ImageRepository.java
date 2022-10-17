package main.java.com.example.restservice.repositories;

import com.example.restservice.models.LImg;

import java.util.List;

public interface ImageRepository {
    List<LImg> getImages(List<String> urls);
    LImg getImage(String url);
}
