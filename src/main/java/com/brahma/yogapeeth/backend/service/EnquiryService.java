package com.brahma.yogapeeth.backend.service;



import com.brahma.yogapeeth.backend.dto.req.EnquiryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.EnquiryResponseDTO;

import java.util.List;

public interface EnquiryService {

    EnquiryResponseDTO createEnquiry(EnquiryRequestDTO requestDTO);

    List<EnquiryResponseDTO> getAllEnquiries();

    EnquiryResponseDTO getEnquiryById(Long id);

    EnquiryResponseDTO updateStatus(Long id, String status);

    void deleteEnquiry(Long id);
}
