package com.rrbuilders.backend.controller;

import com.rrbuilders.backend.dto.ApiResponse;
import com.rrbuilders.backend.entity.Property;
import com.rrbuilders.backend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Property>>> getAllProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String bhk) {
        List<Property> properties = propertyService.getAllProperties(location, type, status, bhk);
        return ResponseEntity.ok(ApiResponse.success("Properties fetched successfully", properties));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Property>> getPropertyById(@PathVariable String id) {
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(ApiResponse.success("Property fetched successfully", property));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<Property>>> getFeaturedProperties() {
        List<Property> properties = propertyService.getFeaturedProperties();
        return ResponseEntity.ok(ApiResponse.success("Featured properties fetched successfully", properties));
    }
}
