package com.rrbuilders.backend.service;

import com.rrbuilders.backend.entity.Property;

import java.util.List;

public interface PropertyService {

    List<Property> getAllProperties(String location, String type, String status, String bhk);

    Property getPropertyById(String id);

    List<Property> getFeaturedProperties();

    Property saveProperty(Property property);
}
