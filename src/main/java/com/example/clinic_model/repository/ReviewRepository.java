package com.example.clinic_model.repository;

import com.example.clinic_model.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
