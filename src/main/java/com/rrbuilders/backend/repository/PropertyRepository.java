package com.rrbuilders.backend.repository;

import com.rrbuilders.backend.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    List<Property> findByFeaturedTrue();

    List<Property> findByLocationSlug(String locationSlug);

    List<Property> findByTypeIgnoreCase(String type);

    List<Property> findByStatusIgnoreCase(String status);

    @Query("SELECT p FROM Property p WHERE " +
           "(:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')) OR LOWER(p.locationSlug) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:type IS NULL OR :type = '' OR LOWER(p.type) = LOWER(:type)) AND " +
           "(:status IS NULL OR :status = '' OR LOWER(p.status) = LOWER(:status)) AND " +
           "(:bhk IS NULL OR :bhk = '' OR LOWER(p.bhk) LIKE LOWER(CONCAT('%', :bhk, '%')))")
    List<Property> searchProperties(
            @Param("location") String location,
            @Param("type") String type,
            @Param("status") String status,
            @Param("bhk") String bhk
    );
}
