package com.example.clinic_model.dto;

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

    @Future(message = "Appointment follow-up date and time must be in the future")
    private LocalDate followUpDateAndTime;

    private String category;

    private String name;
    private String email;
    private Long phoneNumber;
    private LocalDate dateOfBirth;

    //relations
    private DoctorDTO doctor; // Update field name to doctor_id
    private PatientDTO patient; // Update field name to patient_id
}

