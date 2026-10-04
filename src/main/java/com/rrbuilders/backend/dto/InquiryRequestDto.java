package com.rrbuilders.backend.dto;

import com.rrbuilders.backend.entity.InquiryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryRequestDto {

    private InquiryType inquiryType;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+\\-\\s()]{7,20}$", message = "Please provide a valid phone number")
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

    // Client-specified Buy / Sell format fields
    private String selectedPropertyTypes;

    @com.fasterxml.jackson.annotation.JsonSetter("selectedPropertyTypes")
    public void setDeserializedPropertyTypes(Object types) {
        if (types instanceof java.util.List<?> list) {
            this.selectedPropertyTypes = String.join(", ", list.stream().map(Object::toString).toList());
        } else if (types != null) {
            this.selectedPropertyTypes = types.toString();
        } else {
            this.selectedPropertyTypes = null;
        }
    }

    private String timelineUrgency;

    private String demandAskingPrice;

    private String selectedPackage;
}
