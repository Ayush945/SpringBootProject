package com.example.clinic_model.service;

import com.example.clinic_model.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewDTO createReviewForPatient(ReviewDTO reviewDTO, Long patientId);

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long reviewId);

    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);

    void deleteReviewById(Long reviewId);
}
