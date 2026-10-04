package com.rrbuilders.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "inquiry_type", nullable = false)
    private InquiryType inquiryType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    private String email;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "service_interest")
    private String serviceInterest;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "reference_title")
    private String referenceTitle;

    @Column(name = "plot_location")
    private String plotLocation;

    @Column(name = "plot_size")
    private String plotSize;

    @Column(name = "construction_type")
    private String constructionType;

    private String budget;

    @Column(name = "expected_price")
    private String expectedPrice;

    @Column(name = "property_type")
    private String propertyType;

    // Client-specified Buy / Sell format fields
    @Column(name = "selected_property_types")
    private String selectedPropertyTypes;

    @Column(name = "timeline_urgency")
    private String timelineUrgency;

    @Column(name = "demand_asking_price")
    private String demandAskingPrice;

    @Column(name = "selected_package")
    private String selectedPackage;

    @Column(name = "status")
    @Builder.Default
    private String status = "NEW";

    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
