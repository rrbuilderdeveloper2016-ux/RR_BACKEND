package com.rrbuilders.backend.repository;

import com.rrbuilders.backend.entity.Inquiry;
import com.rrbuilders.backend.entity.InquiryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findAllByOrderByCreatedAtDesc();

    List<Inquiry> findByInquiryTypeOrderByCreatedAtDesc(InquiryType inquiryType);
}
