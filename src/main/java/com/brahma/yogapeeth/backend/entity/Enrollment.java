package com.brahma.yogapeeth.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String country;
    private String paymentMode;
    private String paymentStatus;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    @JsonBackReference
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
