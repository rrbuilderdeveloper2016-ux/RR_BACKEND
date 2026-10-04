package com.rrbuilders.backend.controller;

import com.rrbuilders.backend.dto.ApiResponse;
import com.rrbuilders.backend.entity.Location;
import com.rrbuilders.backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Location>>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(ApiResponse.success("Locations fetched successfully", locations));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<Location>> getLocationBySlug(@PathVariable String slug) {
        Location location = locationService.getLocationBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Location fetched successfully", location));
    }
}
