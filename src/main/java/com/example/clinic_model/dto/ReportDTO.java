package com.example.clinic_model.dto;

import com.example.clinic_model.model.Department;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Patient;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReportDTO {
    private Long reportId;

    @NotNull(message = "Report date is required")
    private LocalDate reportDate;


    private byte[] reportFile;


    //relations
    private PatientDTO patient;
    private DoctorDTO doctor;

}
