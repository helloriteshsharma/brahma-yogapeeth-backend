package com.brahma.yogapeeth.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String shortDescription;

    @Column(length = 5000)
    private String description;

    private String schedule;

    private Double price;

    private String mode;

    private String imageUrl;

    private Boolean active = true;
}
