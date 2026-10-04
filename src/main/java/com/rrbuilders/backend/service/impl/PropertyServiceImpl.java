package com.rrbuilders.backend.service.impl;

import com.rrbuilders.backend.entity.Property;
import com.rrbuilders.backend.exception.ResourceNotFoundException;
import com.rrbuilders.backend.repository.PropertyRepository;
import com.rrbuilders.backend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    public List<Property> getAllProperties(String location, String type, String status, String bhk) {
        if ((location == null || location.isBlank()) &&
            (type == null || type.isBlank()) &&
            (status == null || status.isBlank()) &&
            (bhk == null || bhk.isBlank())) {
            return propertyRepository.findAll();
        }
        return propertyRepository.searchProperties(
                (location != null && !location.isBlank()) ? location.trim() : null,
                (type != null && !type.isBlank()) ? type.trim() : null,
                (status != null && !status.isBlank()) ? status.trim() : null,
                (bhk != null && !bhk.isBlank()) ? bhk.trim() : null
        );
    }

    @Override
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
    }

    @Override
    public List<Property> getFeaturedProperties() {
        return propertyRepository.findByFeaturedTrue();
    }

    @Override
    @Transactional
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }
}
