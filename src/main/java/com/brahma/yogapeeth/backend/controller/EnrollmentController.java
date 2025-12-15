package com.brahma.yogapeeth.backend.controller;


import com.brahma.yogapeeth.backend.dto.req.EnrollmentPaymentDTO;
import com.brahma.yogapeeth.backend.dto.req.EnrollmentRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnrollmentResponseDTO;
import com.brahma.yogapeeth.backend.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(
            @Valid @RequestBody EnrollmentRequestDTO requestDTO) {
        EnrollmentResponseDTO response = enrollmentService.createEnrollment(requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentRequestDTO requestDTO) {

        EnrollmentResponseDTO updated = enrollmentService.updateEnrollment(id, requestDTO);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<EnrollmentResponseDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestBody EnrollmentPaymentDTO paymentDTO) {

        EnrollmentResponseDTO updated = enrollmentService.updatePaymentStatus(id, paymentDTO.getPaymentStatus());
        return ResponseEntity.ok(updated);
    }

}
