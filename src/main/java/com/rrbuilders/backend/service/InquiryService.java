package com.rrbuilders.backend.service;

import com.rrbuilders.backend.dto.InquiryRequestDto;
import com.rrbuilders.backend.dto.InquiryResponseDto;
import com.rrbuilders.backend.entity.InquiryType;

import java.util.List;

public interface InquiryService {

    InquiryResponseDto submitInquiry(InquiryRequestDto requestDto);

    List<InquiryResponseDto> getAllInquiries();

    List<InquiryResponseDto> getInquiriesByType(InquiryType inquiryType);

    InquiryResponseDto updateInquiryStatus(Long id, String status, String adminNotes);

    void deleteInquiry(Long id);

    java.util.Map<String, Object> getInquiryStats();
}
