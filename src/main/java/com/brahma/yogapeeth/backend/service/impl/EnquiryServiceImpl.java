package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.dto.req.EnquiryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnquiryResponseDTO;
import com.brahma.yogapeeth.backend.entity.Enquiry;
import com.brahma.yogapeeth.backend.repository.EnquiryRepository;
import com.brahma.yogapeeth.backend.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {

    private final EnquiryRepository enquiryRepository;

    @Override
    public EnquiryResponseDTO createEnquiry(EnquiryRequestDTO requestDTO) {
        Enquiry enquiry = Enquiry.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .message(requestDTO.getMessage())
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .build();

        Enquiry saved = enquiryRepository.save(enquiry);
        return mapToDTO(saved);
    }

    @Override
    public List<EnquiryResponseDTO> getAllEnquiries() {
        return enquiryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnquiryResponseDTO getEnquiryById(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enquiry not found"));
        return mapToDTO(enquiry);
    }

    @Override
    public EnquiryResponseDTO updateStatus(Long id, String status) {
        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enquiry not found"));

        enquiry.setStatus(status);
        Enquiry updated = enquiryRepository.save(enquiry);
        return mapToDTO(updated);
    }

    @Override
    public void deleteEnquiry(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enquiry not found"));
        enquiryRepository.delete(enquiry);
    }

    private EnquiryResponseDTO mapToDTO(Enquiry enquiry) {
        return EnquiryResponseDTO.builder()
                .id(enquiry.getId())
                .name(enquiry.getName())
                .email(enquiry.getEmail())
                .phone(enquiry.getPhone())
                .message(enquiry.getMessage())
                .status(enquiry.getStatus())
                .createdAt(enquiry.getCreatedAt())
                .build();
    }
}
