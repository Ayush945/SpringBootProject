package com.example.clinic_model.controller;

import com.example.clinic_model.dto.ReviewDTO; // Import the ReviewDTO class
import com.example.clinic_model.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController
{
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }

//    @PostMapping
//    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO)
//    {
//        ReviewDTO createdReview = this.reviewService.createReview(reviewDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
//    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO,
                                                  @PathVariable("patientId") Long patientId)
    {
        ReviewDTO createdReview = this.reviewService.createReviewForPatient(reviewDTO, patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("id") Long reviewId)
    {
        ReviewDTO reviewDTO = this.reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews()
    {
        List<ReviewDTO> reviewDTOs = this.reviewService.getAllReviews();
        return ResponseEntity.ok(reviewDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable("id") Long reviewId,
                                                      @RequestBody ReviewDTO reviewDTO)
    {
        ReviewDTO updatedReview = this.reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable("id") Long reviewId)
    {
        this.reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
