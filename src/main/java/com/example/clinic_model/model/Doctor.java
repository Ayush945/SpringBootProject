package com.example.clinic_model.model;

import com.example.clinic_model.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor extends User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String doctorName;
    private String specialization;
    private final RoleEnum role = RoleEnum.ROLE_DOCTOR;
    private Long doctorLicense;
    private String doctorBio;


    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private List<Appointment> appointments;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_department",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments = new ArrayList<>();


    @OneToOne(mappedBy = "doctor")
    @JsonBackReference
    private Image image;

    private boolean isVerified=false;
}
