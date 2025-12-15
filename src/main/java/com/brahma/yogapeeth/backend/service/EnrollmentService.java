package com.brahma.yogapeeth.backend.service;

import com.brahma.yogapeeth.backend.dto.req.EnrollmentRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO requestDTO);

    List<EnrollmentResponseDTO> getAllEnrollments();

    EnrollmentResponseDTO getEnrollmentById(Long id);

    List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId);

    void deleteEnrollment(Long id);

    EnrollmentResponseDTO updateEnrollment(Long id,  EnrollmentRequestDTO requestDTO);

    EnrollmentResponseDTO updatePaymentStatus(Long id, String paymentStatus);

}
