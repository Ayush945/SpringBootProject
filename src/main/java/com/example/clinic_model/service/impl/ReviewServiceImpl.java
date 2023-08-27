package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.dto.ReviewDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.model.Review;
import com.example.clinic_model.repository.ReviewRepository;
import com.example.clinic_model.service.PatientService;
import com.example.clinic_model.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientService patientService;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        review = reviewRepository.save(review);
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO createReviewForPatient(ReviewDTO reviewDTO, Long patientId) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        PatientDTO patientDTO = this.patientService.getPatientById(patientId);
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        review.setPatient(patient);
        Review savedReview = this.reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found!"));
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (optionalReview.isEmpty()) {
            throw new ResourceNotFoundException("Review not found");
        }

        Review existingReview = optionalReview.get();

        // Update properties from DTO
        if (reviewDTO.getReviewComment() != null) {
            existingReview.setReviewComment(reviewDTO.getReviewComment());
        }

        Review updatedReview = reviewRepository.save(existingReview);

        return modelMapper.map(updatedReview, ReviewDTO.class);
    }

    @Override
    public void deleteReviewById(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Review not found!");
        }
        reviewRepository.deleteById(reviewId);
    }
}
