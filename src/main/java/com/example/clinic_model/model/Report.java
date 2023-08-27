package com.example.clinic_model.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private LocalDate reportDate;

    @Lob
    private byte[] reportFile;



    @ManyToOne
    @JoinColumn(name = "patient_ids")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_ids")
    private Doctor doctor;

}
