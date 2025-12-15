package com.brahma.yogapeeth.backend.service;


import com.brahma.yogapeeth.backend.dto.req.TestimonialRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.TestimonialResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestimonialService {

    TestimonialResponseDTO createTestimonial(TestimonialRequestDTO requestDTO, MultipartFile file);

    List<TestimonialResponseDTO> getAllTestimonials();

    TestimonialResponseDTO getTestimonialById(Long id);

    TestimonialResponseDTO updateTestimonial(Long id, TestimonialRequestDTO requestDTO, MultipartFile file);

    void deleteTestimonial(Long id);
}
