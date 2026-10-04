package com.rrbuilders.backend.controller;

import com.rrbuilders.backend.dto.ApiResponse;
import com.rrbuilders.backend.dto.InquiryRequestDto;
import com.rrbuilders.backend.dto.InquiryResponseDto;
import com.rrbuilders.backend.entity.InquiryType;
import com.rrbuilders.backend.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitInquiry(@Valid @RequestBody InquiryRequestDto requestDto) {
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inquiry submitted successfully", response));
    }

    @PostMapping("/consultation")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitConsultation(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.CONSULTATION);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Consultation request submitted successfully", response));
    }

    @PostMapping("/buy-requirement")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitBuyRequirement(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.PROPERTY_ENQUIRY);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Buy requirement recorded successfully", response));
    }

    @PostMapping("/sell-requirement")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitSellRequirement(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.SELL_PROPERTY);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sell property requirement recorded successfully", response));
    }

    @PostMapping("/property")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitPropertyEnquiry(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.PROPERTY_ENQUIRY);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Property inquiry submitted successfully", response));
    }

    @PostMapping("/project")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitProjectEnquiry(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.PROJECT_ENQUIRY);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project inquiry submitted successfully", response));
    }

    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitSellProperty(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.SELL_PROPERTY);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Property listing request submitted successfully", response));
    }

    @PostMapping("/build-plot")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitBuildOnPlot(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.BUILD_ON_PLOT);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Construction estimate request submitted successfully", response));
    }

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> submitContactMessage(@Valid @RequestBody InquiryRequestDto requestDto) {
        requestDto.setInquiryType(InquiryType.CONTACT_GENERAL);
        InquiryResponseDto response = inquiryService.submitInquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Contact message received successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InquiryResponseDto>>> getAllInquiries(
            @RequestParam(required = false) InquiryType type) {
        List<InquiryResponseDto> list = (type != null) ?
                inquiryService.getInquiriesByType(type) :
                inquiryService.getAllInquiries();
        return ResponseEntity.ok(ApiResponse.success("Inquiries retrieved successfully", list));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<java.util.Map<String, Object>>> getInquiryStats() {
        return ResponseEntity.ok(ApiResponse.success("Inquiry statistics retrieved successfully", inquiryService.getInquiryStats()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<InquiryResponseDto>> updateInquiryStatus(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body) {
        String status = body.get("status");
        String notes = body.get("adminNotes");
        InquiryResponseDto updated = inquiryService.updateInquiryStatus(id, status, notes);
        return ResponseEntity.ok(ApiResponse.success("Inquiry status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(ApiResponse.success("Inquiry deleted successfully", null));
    }
}
