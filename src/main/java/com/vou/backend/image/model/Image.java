package com.vou.backend.image.model;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "images")
@Getter
@Setter
public class Image {
    @Id
    private String id;
    private String fileName; // Name of the file
    private String contentType; // Image mime type
    @Field("content")
    private Binary content; // Avatar image as Binary
}
