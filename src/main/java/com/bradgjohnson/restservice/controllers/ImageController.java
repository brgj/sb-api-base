package com.bradgjohnson.restservice.controllers;

import com.bradgjohnson.restservice.models.ImageRequest;
import com.bradgjohnson.restservice.models.LImg;
import com.bradgjohnson.restservice.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/images")
@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping
    public List<LImg> GetImages(@RequestBody ImageRequest imageRequest) {
        return imageService.getImages(imageRequest.urls);
    }
}
