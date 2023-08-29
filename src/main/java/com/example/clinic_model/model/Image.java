package com.example.clinic_model.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String fileName;

    @OneToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "reportId")
    private Report report;

    @OneToOne
    @JoinColumn(name = "newsId")
    private News news;

    public Image(String fileName){
        this.fileName=fileName;
    }
}
