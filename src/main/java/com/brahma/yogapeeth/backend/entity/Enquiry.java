package com.brahma.yogapeeth.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enquiries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Column(length = 2000)
    private String message;

    private LocalDateTime createdAt;

    private String status;
}
