package main.java.com.example.restservice.controllers;

import com.example.restservice.models.ImageRequest;
import com.example.restservice.models.LImg;
import com.example.restservice.services.ImageService;
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
