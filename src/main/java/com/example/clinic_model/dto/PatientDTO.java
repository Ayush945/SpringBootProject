package com.example.clinic_model.dto;

import com.example.clinic_model.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO extends UserDTO{
    private Long patientId;

    @NotBlank(message = "Patient name cannot be blank")
    @Size(max = 100, message = "Patient name must be less than 100 characters")
    private String patientName;

    @Positive(message = "Age must be a valid positive number")
    @Max(value = 150, message = "Age must be less than 150")
    private int age;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    private final RoleEnum role = RoleEnum.ROLE_PATIENT;
    private int otp;

    private boolean isVerified= false;

    //relations
    @JsonBackReference
    private ReviewDTO review;


}
