package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.dto.req.TestimonialRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.TestimonialResponseDTO;
import com.brahma.yogapeeth.backend.entity.Testimonial;
import com.brahma.yogapeeth.backend.repository.TestimonialRepository;
import com.brahma.yogapeeth.backend.service.AwsS3Service;
import com.brahma.yogapeeth.backend.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public TestimonialResponseDTO createTestimonial(TestimonialRequestDTO requestDTO, MultipartFile photo) {
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            photoUrl = awsS3Service.uploadFile(photo);
        }

        Testimonial testimonial = Testimonial.builder()
                .name(requestDTO.getName())
                .feedback(requestDTO.getFeedback())
                .rating(requestDTO.getRating())
                .photoUrl(photoUrl)
                .createdAt(LocalDateTime.now())
                .build();

        Testimonial saved = testimonialRepository.save(testimonial);
        return mapToDTO(saved);
    }

    @Override
    public List<TestimonialResponseDTO> getAllTestimonials() {
        return testimonialRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TestimonialResponseDTO getTestimonialById(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Testimonial not found"));
        return mapToDTO(testimonial);
    }

    @Override
    public TestimonialResponseDTO updateTestimonial(Long id, TestimonialRequestDTO requestDTO, MultipartFile photo) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Testimonial not found"));

        testimonial.setName(requestDTO.getName());
        testimonial.setFeedback(requestDTO.getFeedback());
        testimonial.setRating(requestDTO.getRating());

        if (photo != null && !photo.isEmpty()) {
            if (testimonial.getPhotoUrl() != null && !testimonial.getPhotoUrl().isEmpty()) {
                String oldFileName = testimonial.getPhotoUrl().substring(testimonial.getPhotoUrl().lastIndexOf("/") + 1);
                awsS3Service.deleteFile(oldFileName);
            }
            String newPhotoUrl = awsS3Service.uploadFile(photo);
            testimonial.setPhotoUrl(newPhotoUrl);
        }

        Testimonial updated = testimonialRepository.save(testimonial);
        return mapToDTO(updated);
    }

    @Override
    public void deleteTestimonial(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Testimonial not found"));

        if (testimonial.getPhotoUrl() != null && !testimonial.getPhotoUrl().isEmpty()) {
            String fileName = testimonial.getPhotoUrl().substring(testimonial.getPhotoUrl().lastIndexOf("/") + 1);
            awsS3Service.deleteFile(fileName);
        }

        testimonialRepository.delete(testimonial);
    }

    private TestimonialResponseDTO mapToDTO(Testimonial t) {
        return TestimonialResponseDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .feedback(t.getFeedback())
                .rating(t.getRating())
                .photoUrl(t.getPhotoUrl())
                .createdAt(t.getCreatedAt())
                .build();
    }
}
