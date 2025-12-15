package com.brahma.yogapeeth.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String category;

    private LocalDateTime createdAt;
}
