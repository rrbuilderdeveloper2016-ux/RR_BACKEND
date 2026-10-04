package com.rrbuilders.backend.dto;

import com.rrbuilders.backend.entity.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryResponseDto {

    private Long id;
    private InquiryType inquiryType;
    private String name;
    private String phone;
    private String email;
    private String subject;
    private String message;
    private String serviceInterest;
    private String preferredLocation;
    private String referenceId;
    private String referenceTitle;
    private String plotLocation;
    private String plotSize;
    private String constructionType;
    private String budget;
    private String expectedPrice;
    private String propertyType;
    private String selectedPropertyTypes;
    private String timelineUrgency;
    private String demandAskingPrice;
    private String selectedPackage;
    private String status;
    private String adminNotes;
    private LocalDateTime createdAt;
}
