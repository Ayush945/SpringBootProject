package com.example.clinic_model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO {
    private Long imageId;
    private String fileName;
    private MultipartFile image;

    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;

    private ReportDTO reportDTO;

    private NewsDTO newsDTO;
}
