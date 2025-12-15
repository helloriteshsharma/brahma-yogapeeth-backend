package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.config.EmailConfig;
import com.brahma.yogapeeth.backend.dto.req.EnrollmentRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnrollmentResponseDTO;
import com.brahma.yogapeeth.backend.entity.Accommodation;
import com.brahma.yogapeeth.backend.entity.Course;
import com.brahma.yogapeeth.backend.entity.Enrollment;
import com.brahma.yogapeeth.backend.exception.ResourceNotFoundException;
import com.brahma.yogapeeth.backend.repository.AccommodationRepository;
import com.brahma.yogapeeth.backend.repository.CourseRepository;
import com.brahma.yogapeeth.backend.repository.EnrollmentRepository;
import com.brahma.yogapeeth.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final AccommodationRepository accommodationRepository;

    private final SesClient sesClient;
    private final EmailConfig emailConfig;

    @Override
    public EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO requestDTO) {

        Course course = courseRepository.findById(requestDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setFullName(requestDTO.getFullName());
        enrollment.setEmail(requestDTO.getEmail());
        enrollment.setPhone(requestDTO.getPhone());
        enrollment.setGender(requestDTO.getGender());
        enrollment.setCountry(requestDTO.getCountry());
        enrollment.setCourse(course);
        enrollment.setPaymentMode(requestDTO.getPaymentMode());

        enrollment.setPaymentStatus(
                "PAY_LATER".equalsIgnoreCase(requestDTO.getPaymentMode()) ? "PENDING" : "PAID"
        );

        if (requestDTO.getAccommodationId() != null) {
            Accommodation accommodation = accommodationRepository.findById(requestDTO.getAccommodationId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Accommodation not found with id: " + requestDTO.getAccommodationId()
                    ));
            enrollment.setAccommodation(accommodation);
        } else {
            enrollment.setAccommodation(null);
        }

        enrollment.setCreatedAt(LocalDateTime.now());

        Enrollment saved = enrollmentRepository.save(enrollment);

        sendUserEmail(saved);
        sendAdminNotification(saved);

        return mapToResponseDTO(saved);
    }

    @Override
    public EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentRequestDTO requestDTO) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));

        // Update basic fields
        enrollment.setFullName(requestDTO.getFullName());
        enrollment.setEmail(requestDTO.getEmail());
        enrollment.setPhone(requestDTO.getPhone());
        enrollment.setGender(requestDTO.getGender());
        enrollment.setCountry(requestDTO.getCountry());

        // Update course
        Course course = courseRepository.findById(requestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + requestDTO.getCourseId()));
        enrollment.setCourse(course);

        // Update accommodation
        if (requestDTO.getAccommodationId() != null) {
            Accommodation accommodation = accommodationRepository.findById(requestDTO.getAccommodationId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Accommodation not found with id: " + requestDTO.getAccommodationId()
                    ));
            enrollment.setAccommodation(accommodation);
        } else {
            enrollment.setAccommodation(null);
        }

        // Update payment info
        if (requestDTO.getPaymentMode() != null) enrollment.setPaymentMode(requestDTO.getPaymentMode());
        if (requestDTO.getPaymentStatus() != null) enrollment.setPaymentStatus(requestDTO.getPaymentStatus());

        Enrollment saved = enrollmentRepository.save(enrollment);

        if ("PAID".equalsIgnoreCase(saved.getPaymentStatus())) {
            sendUserEmail(saved);
            sendAdminNotification(saved);
        }

        return mapToResponseDTO(saved);
    }

    @Override
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        return mapToResponseDTO(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }

    private EnrollmentResponseDTO mapToResponseDTO(Enrollment enrollment) {
        return EnrollmentResponseDTO.builder()
                .id(enrollment.getId())
                .fullName(enrollment.getFullName())
                .email(enrollment.getEmail())
                .phone(enrollment.getPhone())
                .gender(enrollment.getGender())
                .country(enrollment.getCountry())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .accommodationId(enrollment.getAccommodation() != null ? enrollment.getAccommodation().getId() : null)
                .accommodationType(enrollment.getAccommodation() != null ? enrollment.getAccommodation().getType() : "None")
                .accommodationPrice(enrollment.getAccommodation() != null ? enrollment.getAccommodation().getPrice() : 0.0)
                .createdAt(enrollment.getCreatedAt())
                .paymentMode(enrollment.getPaymentMode())
                .paymentStatus(enrollment.getPaymentStatus())
                .build();
    }

    private void sendUserEmail(Enrollment enrollment) {
        String subject = "Yoga Course Enrollment Confirmation";
        String body = String.format(
                "Hello %s,\n\nYou have successfully enrolled in the course: %s.\n" +
                        "Schedule: %s\nPayment Status: %s\nAccommodation: %s\nPrice: ₹%.2f\n\nThank you!",
                enrollment.getFullName(),
                enrollment.getCourse().getTitle(),
                enrollment.getCourse().getSchedule(),
                enrollment.getPaymentStatus(),
                enrollment.getAccommodation() != null ? enrollment.getAccommodation().getType() : "None",
                enrollment.getAccommodation() != null ? enrollment.getAccommodation().getPrice() : 0.0
        );

        sendEmail(enrollment.getEmail(), subject, body);
    }

    private void sendAdminNotification(Enrollment enrollment) {
        String adminEmail = emailConfig.getAdmin();
        String subject = "New Enrollment: " + enrollment.getFullName();
        String body = String.format(
                "New enrollment received.\n\nName: %s\nEmail: %s\nPhone: %s\nCourse: %s\nPayment Mode: %s\nPayment Status: %s\nAccommodation: %s\nPrice: ₹%.2f",
                enrollment.getFullName(),
                enrollment.getEmail(),
                enrollment.getPhone(),
                enrollment.getCourse().getTitle(),
                enrollment.getPaymentMode(),
                enrollment.getPaymentStatus(),
                enrollment.getAccommodation() != null ? enrollment.getAccommodation().getType() : "None",
                enrollment.getAccommodation() != null ? enrollment.getAccommodation().getPrice() : 0.0
        );

        sendEmail(adminEmail, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(toEmail).build())
                    .message(Message.builder()
                            .subject(software.amazon.awssdk.services.ses.model.Content.builder().data(subject).charset("UTF-8").build())
                            .body(Body.builder()
                                    .text(software.amazon.awssdk.services.ses.model.Content.builder().data(body).charset("UTF-8").build())
                                    .build())
                            .build())
                    .source(emailConfig.getSource())
                    .build();

            sesClient.sendEmail(emailRequest);
        } catch (SesException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to send email: " + e.getMessage());
        }
    }

    @Override
    public EnrollmentResponseDTO updatePaymentStatus(Long id, String paymentStatus) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setPaymentStatus(paymentStatus);
        enrollmentRepository.save(enrollment);

        return mapToResponseDTO(enrollment);
    }

}
