package com.brahma.yogapeeth.backend.controller;

import com.brahma.yogapeeth.backend.dto.req.TestimonialRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.TestimonialResponseDTO;
import com.brahma.yogapeeth.backend.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;
    private final ObjectMapper objectMapper; // Inject ObjectMapper

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<TestimonialResponseDTO> createTestimonial(
            @RequestPart("testimonial") String testimonialJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        try {
            TestimonialRequestDTO requestDTO =
                    objectMapper.readValue(testimonialJson, TestimonialRequestDTO.class);

            return ResponseEntity
                    .status(201)
                    .body(testimonialService.createTestimonial(requestDTO, photo));

        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON Format: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TestimonialResponseDTO>> getAllTestimonials() {
        return ResponseEntity.ok(testimonialService.getAllTestimonials());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialResponseDTO> getTestimonialById(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialService.getTestimonialById(id));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<TestimonialResponseDTO> updateTestimonial(
            @PathVariable Long id,
            @RequestPart("testimonial") String testimonialJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        try {
            TestimonialRequestDTO requestDTO =
                    objectMapper.readValue(testimonialJson, TestimonialRequestDTO.class);

            return ResponseEntity.ok(
                    testimonialService.updateTestimonial(id, requestDTO, photo)
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON Format: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}
