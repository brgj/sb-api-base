package com.bradgjohnson.restservice.repositories;

import com.bradgjohnson.restservice.models.ImageSource;
import com.bradgjohnson.restservice.models.LImg;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Repository("http_impl")
public class HttpImageRepository implements ImageRepository {
    @Override
    public List<LImg> getImages(List<String> urls) {
        return null;
    }

    @Override
    public LImg getImage(String url) {
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
            conn.getInputStream();

            return LImg.builder()
                    .url(url)
                    .source(ImageSource.DOWNLOADED)
                    .sizeBytes(conn.getContentLength())
                    .build();
        } catch (IOException e) {
            System.err.println("Error thrown while fetching image: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }
}
