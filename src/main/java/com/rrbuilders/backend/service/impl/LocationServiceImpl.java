package com.rrbuilders.backend.service.impl;

import com.rrbuilders.backend.entity.Location;
import com.rrbuilders.backend.exception.ResourceNotFoundException;
import com.rrbuilders.backend.repository.LocationRepository;
import com.rrbuilders.backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationBySlug(String slug) {
        return locationRepository.findBySlugIgnoreCase(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with slug: " + slug));
    }

    @Override
    @Transactional
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}
