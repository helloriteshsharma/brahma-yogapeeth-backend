package com.brahma.yogapeeth.backend.controller;

import com.brahma.yogapeeth.backend.dto.req.AccommodationRequestDTO;
import com.brahma.yogapeeth.backend.entity.Accommodation;
import com.brahma.yogapeeth.backend.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/accommodations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<Accommodation> addAccommodation(@RequestBody AccommodationRequestDTO dto) {
        return ResponseEntity.ok(accommodationService.addAccommodation(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accommodation> updateAccommodation(@PathVariable Long id,
                                                             @RequestBody AccommodationRequestDTO dto) {
        return ResponseEntity.ok(accommodationService.updateAccommodation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAll() {
        return ResponseEntity.ok(accommodationService.getAllAccommodations());
    }
}
