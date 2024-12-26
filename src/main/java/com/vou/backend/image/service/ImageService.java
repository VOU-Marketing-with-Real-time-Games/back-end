package com.vou.backend.image.service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vou.backend.image.exception.ImageNotFoundException;
import com.vou.backend.image.model.Image;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Image storeImage(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        Binary imageBinary = new Binary(bytes);

        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setContent(imageBinary);

        return mongoTemplate.save(image);
    }

    public Image getImage(String imageId) throws ImageNotFoundException {
        Query query = new Query(Criteria.where("_id").is(imageId));

        Image image = mongoTemplate.findOne(query, Image.class);
        if (image == null) {
            throw new ImageNotFoundException("Image not found");
        }
        return image;
    }

    public void deleteImage(String imageId) throws ImageNotFoundException {
        Query query = new Query(Criteria.where("_id").is(imageId));
        if (mongoTemplate.findOne(query, Image.class) == null) {
            throw new ImageNotFoundException("Image not found");
        }
        mongoTemplate.remove(query, Image.class);
    }
}
