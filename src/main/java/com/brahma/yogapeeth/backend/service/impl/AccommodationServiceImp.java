package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.dto.req.AccommodationRequestDTO;
import com.brahma.yogapeeth.backend.entity.Accommodation;
import com.brahma.yogapeeth.backend.repository.AccommodationRepository;
import com.brahma.yogapeeth.backend.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImp implements AccommodationService {

        private final AccommodationRepository accommodationRepository;

    public Accommodation addAccommodation(AccommodationRequestDTO dto) {
        Accommodation accommodation = Accommodation.builder()
                .type(dto.getType())
                .price(dto.getPrice())
                .active(true)
                .build();
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(Long id, AccommodationRequestDTO dto) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));
        accommodation.setType(dto.getType());
        accommodation.setPrice(dto.getPrice());
        return accommodationRepository.save(accommodation);
    }

    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
        // or soft delete: accommodation.setActive(false);
    }

    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }
}
