package com.vou.backend.image.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vou.backend.image.exception.ImageNotFoundException;
import com.vou.backend.image.exception.ImageNotValidException;
import com.vou.backend.image.model.Image;
import com.vou.backend.image.service.ImageService;

@RestController
@RequestMapping("/image")
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
