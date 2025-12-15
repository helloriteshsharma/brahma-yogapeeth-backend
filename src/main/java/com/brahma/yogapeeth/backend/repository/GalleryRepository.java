package com.brahma.yogapeeth.backend.repository;

import com.brahma.yogapeeth.backend.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
