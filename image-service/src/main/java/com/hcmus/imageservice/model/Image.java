package com.hcmus.imageservice.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
