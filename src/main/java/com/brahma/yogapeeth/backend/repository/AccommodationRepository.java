package com.brahma.yogapeeth.backend.repository;

import com.brahma.yogapeeth.backend.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}