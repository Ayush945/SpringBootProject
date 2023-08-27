package com.example.clinic_model.dto;

import com.example.clinic_model.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Long reviewId;

    @NotBlank(message = "Review comment cannot be blank")
    @Size(max = 1000, message = "Review comment must be less than 1000 characters")
    private String reviewComment;

    private LocalDate reviewDate;


    //relations
    private PatientDTO patient;
}
