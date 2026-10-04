package com.rrbuilders.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String type;

    private String category;

    private String status;

    private String price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String crop;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_amenities", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "amenity")
    @Builder.Default
    private List<String> amenities = new ArrayList<>();
}
