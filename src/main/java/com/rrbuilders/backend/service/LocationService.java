package com.rrbuilders.backend.service;

import com.rrbuilders.backend.entity.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocations();

    Location getLocationBySlug(String slug);

    Location saveLocation(Location location);
}
