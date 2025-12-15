package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class EnrollmentRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private Long accommodationId; // optional

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;

    private String paymentStatus;
}
