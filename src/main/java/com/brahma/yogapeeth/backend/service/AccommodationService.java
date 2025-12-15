package com.brahma.yogapeeth.backend.service;

import com.brahma.yogapeeth.backend.dto.req.AccommodationRequestDTO;
import com.brahma.yogapeeth.backend.entity.Accommodation;


import java.util.List;

public interface AccommodationService {



    public Accommodation addAccommodation(AccommodationRequestDTO dto);

    public Accommodation updateAccommodation(Long id, AccommodationRequestDTO dto);

    public void deleteAccommodation(Long id) ;

    public List<Accommodation> getAllAccommodations();
    }
