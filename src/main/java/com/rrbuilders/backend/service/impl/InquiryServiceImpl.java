package com.rrbuilders.backend.service.impl;

import com.rrbuilders.backend.dto.InquiryRequestDto;
import com.rrbuilders.backend.dto.InquiryResponseDto;
import com.rrbuilders.backend.entity.Inquiry;
import com.rrbuilders.backend.entity.InquiryType;
import com.rrbuilders.backend.repository.InquiryRepository;
import com.rrbuilders.backend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    @Override
    public InquiryResponseDto submitInquiry(InquiryRequestDto requestDto) {
        InquiryType type = requestDto.getInquiryType() != null ? requestDto.getInquiryType() : InquiryType.CONSULTATION;

        Inquiry inquiry = Inquiry.builder()
                .inquiryType(type)
                .name(requestDto.getName().trim())
                .phone(requestDto.getPhone().trim())
                .email(requestDto.getEmail() != null ? requestDto.getEmail().trim() : null)
                .subject(requestDto.getSubject())
                .message(requestDto.getMessage())
                .serviceInterest(requestDto.getServiceInterest())
                .preferredLocation(requestDto.getPreferredLocation())
                .referenceId(requestDto.getReferenceId())
                .referenceTitle(requestDto.getReferenceTitle())
                .plotLocation(requestDto.getPlotLocation())
                .plotSize(requestDto.getPlotSize())
                .constructionType(requestDto.getConstructionType())
                .budget(requestDto.getBudget())
                .expectedPrice(requestDto.getExpectedPrice())
                .propertyType(requestDto.getPropertyType())
                .selectedPropertyTypes(requestDto.getSelectedPropertyTypes())
                .timelineUrgency(requestDto.getTimelineUrgency())
                .demandAskingPrice(requestDto.getDemandAskingPrice())
                .selectedPackage(requestDto.getSelectedPackage())
                .build();

        Inquiry saved = inquiryRepository.save(inquiry);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getAllInquiries() {
        return inquiryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getInquiriesByType(InquiryType inquiryType) {
        return inquiryRepository.findByInquiryTypeOrderByCreatedAtDesc(inquiryType)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public InquiryResponseDto updateInquiryStatus(Long id, String status, String adminNotes) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found with ID: " + id));

        if (status != null && !status.trim().isEmpty()) {
            inquiry.setStatus(status.trim().toUpperCase());
        }
        if (adminNotes != null) {
            inquiry.setAdminNotes(adminNotes);
        }

        Inquiry updated = inquiryRepository.save(inquiry);
        return mapToDto(updated);
    }

    @Override
    public void deleteInquiry(Long id) {
        if (!inquiryRepository.existsById(id)) {
            throw new IllegalArgumentException("Inquiry not found with ID: " + id);
        }
        inquiryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getInquiryStats() {
        List<Inquiry> all = inquiryRepository.findAll();
        long total = all.size();
        long newCount = all.stream().filter(i -> i.getStatus() == null || "NEW".equalsIgnoreCase(i.getStatus())).count();
        long contactedCount = all.stream().filter(i -> "CONTACTED".equalsIgnoreCase(i.getStatus())).count();
        long convertedCount = all.stream().filter(i -> "CONVERTED".equalsIgnoreCase(i.getStatus())).count();

        long buyCount = all.stream().filter(i -> 
                i.getInquiryType() == InquiryType.PROPERTY_ENQUIRY || 
                (i.getServiceInterest() != null && i.getServiceInterest().toLowerCase().contains("buy"))
        ).count();

        long sellCount = all.stream().filter(i -> 
                i.getInquiryType() == InquiryType.SELL_PROPERTY || 
                (i.getServiceInterest() != null && i.getServiceInterest().toLowerCase().contains("sell"))
        ).count();

        long buildCount = all.stream().filter(i -> 
                i.getInquiryType() == InquiryType.BUILD_ON_PLOT || 
                (i.getServiceInterest() != null && i.getServiceInterest().toLowerCase().contains("build"))
        ).count();

        long consultationCount = all.stream().filter(i -> 
                i.getInquiryType() == InquiryType.CONSULTATION
        ).count();

        java.util.Map<String, Object> stats = new java.util.LinkedHashMap<>();
        stats.put("total", total);
        stats.put("newCount", newCount);
        stats.put("contactedCount", contactedCount);
        stats.put("convertedCount", convertedCount);
        stats.put("buyCount", buyCount);
        stats.put("sellCount", sellCount);
        stats.put("buildCount", buildCount);
        stats.put("consultationCount", consultationCount);
        return stats;
    }

    private InquiryResponseDto mapToDto(Inquiry entity) {
        return InquiryResponseDto.builder()
                .id(entity.getId())
                .inquiryType(entity.getInquiryType())
                .name(entity.getName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .subject(entity.getSubject())
                .message(entity.getMessage())
                .serviceInterest(entity.getServiceInterest())
                .preferredLocation(entity.getPreferredLocation())
                .referenceId(entity.getReferenceId())
                .referenceTitle(entity.getReferenceTitle())
                .plotLocation(entity.getPlotLocation())
                .plotSize(entity.getPlotSize())
                .constructionType(entity.getConstructionType())
                .budget(entity.getBudget())
                .expectedPrice(entity.getExpectedPrice())
                .propertyType(entity.getPropertyType())
                .selectedPropertyTypes(entity.getSelectedPropertyTypes())
                .timelineUrgency(entity.getTimelineUrgency())
                .demandAskingPrice(entity.getDemandAskingPrice())
                .selectedPackage(entity.getSelectedPackage())
                .status(entity.getStatus() != null ? entity.getStatus() : "NEW")
                .adminNotes(entity.getAdminNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
