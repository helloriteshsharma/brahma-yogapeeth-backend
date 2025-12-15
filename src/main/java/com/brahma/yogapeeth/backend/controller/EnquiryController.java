package com.brahma.yogapeeth.backend.controller;


import com.brahma.yogapeeth.backend.dto.req.EnquiryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnquiryResponseDTO;
import com.brahma.yogapeeth.backend.service.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EnquiryController {

    private final EnquiryService enquiryService;

    @PostMapping
    public ResponseEntity<EnquiryResponseDTO> createEnquiry(@Valid @RequestBody EnquiryRequestDTO requestDTO) {
        return ResponseEntity.status(201).body(enquiryService.createEnquiry(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<EnquiryResponseDTO>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnquiryResponseDTO> getEnquiryById(@PathVariable Long id) {
        return ResponseEntity.ok(enquiryService.getEnquiryById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EnquiryResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(enquiryService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnquiry(@PathVariable Long id) {
        enquiryService.deleteEnquiry(id);
        return ResponseEntity.noContent().build();
    }
}
