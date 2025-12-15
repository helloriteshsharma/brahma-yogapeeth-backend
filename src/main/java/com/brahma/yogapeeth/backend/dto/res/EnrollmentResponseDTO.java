package com.brahma.yogapeeth.backend.dto.res;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String country;

    private Long courseId;
    private String courseTitle;

    private Long accommodationId;
    private String accommodationType;
    private Double accommodationPrice;

    private LocalDateTime createdAt;
    private String paymentMode;
    private String paymentStatus;
}
