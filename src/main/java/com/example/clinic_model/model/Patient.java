package com.example.clinic_model.model;

import com.example.clinic_model.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient extends User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String patientName;
    private int age;
    private String address;
    private final RoleEnum role = RoleEnum.ROLE_PATIENT;
    private int otp;
    private boolean isVerified= false;


    @JsonBackReference
    @OneToOne(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private Review review;


    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private List<Appointment> appointments;

    @OneToOne(mappedBy = "patient")
    @JsonBackReference
    private Image image;
}

