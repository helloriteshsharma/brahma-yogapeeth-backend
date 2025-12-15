package com.brahma.yogapeeth.backend.controller;

import com.brahma.yogapeeth.backend.dto.req.GalleryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.GalleryResponseDTO;
import com.brahma.yogapeeth.backend.service.GalleryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GalleryController {

    private final GalleryService galleryService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<GalleryResponseDTO> createGallery(
            @RequestPart("gallery") String galleryJson,
            @RequestPart("image") MultipartFile image) {

        try {
            // Convert JSON string to DTO
            GalleryRequestDTO requestDTO = objectMapper.readValue(galleryJson, GalleryRequestDTO.class);

            return ResponseEntity
                    .status(201)
                    .body(galleryService.createGallery(requestDTO, image));

        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON Format: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<GalleryResponseDTO>> getAllGallery() {
        return ResponseEntity.ok(galleryService.getAllGallery());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryResponseDTO> getGalleryById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getGalleryById(id));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<GalleryResponseDTO> updateGallery(
            @PathVariable Long id,
            @RequestPart("gallery") String galleryJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        try {
            GalleryRequestDTO requestDTO = objectMapper.readValue(galleryJson, GalleryRequestDTO.class);

            return ResponseEntity.ok(galleryService.updateGallery(id, requestDTO, image));

        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON Format: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.noContent().build();
    }
}
