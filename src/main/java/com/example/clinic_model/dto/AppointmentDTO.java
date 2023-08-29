package com.example.clinic_model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppointmentDTO {
    private Long appointmentId;

    @NotNull
    @FutureOrPresent(message = "Appointment date must be in the future or present")
    private LocalDate appointmentDate;

    @NotNull(message = "Missing appointment time")
    private LocalTime appointmentTime;

    @NotBlank(message = "Appointment description cannot be blank")
    private String appointmentDescription;


    private String category;

    private String name;
    private String email;
    private Long phoneNumber;
    private LocalDate dateOfBirth;

    //relations
    @JsonBackReference
    private ReportDTO report;
    private DoctorDTO doctor; // Update field name to doctor_id
    private PatientDTO patient; // Update field name to patient_id
}

