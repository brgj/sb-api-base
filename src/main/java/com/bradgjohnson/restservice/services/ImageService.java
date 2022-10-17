package com.bradgjohnson.restservice.services;

import com.bradgjohnson.restservice.models.LImg;
import com.bradgjohnson.restservice.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    @Qualifier("cached_impl")
    ImageRepository repositoryImpl;

    public List<LImg> getImages(List<String> urls) {
        return repositoryImpl.getImages(urls);
    }
}
