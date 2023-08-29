package com.example.clinic_model.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne(mappedBy = "report")
    @JsonBackReference
    private Image image;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
