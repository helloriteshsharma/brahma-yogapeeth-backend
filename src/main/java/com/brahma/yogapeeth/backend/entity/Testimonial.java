package com.brahma.yogapeeth.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "testimonials")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String feedback;

    private Integer rating;

    private String photoUrl;

    private LocalDateTime createdAt;
}
