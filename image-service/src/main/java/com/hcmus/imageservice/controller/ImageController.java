package com.hcmus.imageservice.controller;

import com.hcmus.imageservice.exception.ImageNotFoundException;
import com.hcmus.imageservice.exception.ImageNotValidException;
import com.hcmus.imageservice.model.Image;
import com.hcmus.imageservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v3/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping()
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file)
            throws ImageNotValidException, IOException {
        Image image = imageService.storeImage(file);
        return ResponseEntity.ok(image.getId());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable String imageId) throws ImageNotFoundException {
        Image image = imageService.getImage(imageId);
        return ResponseEntity.ok()
                .header("Content-Type", image.getContentType())
                .body(image.getContent().getData());
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteAvatar(@PathVariable String imageId) throws ImageNotFoundException {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Image deleted successfully");
    }
}
