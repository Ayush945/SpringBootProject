package com.example.clinic_model.dto;

import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DepartmentDTO {
    private Long departmentId;

    @NotBlank(message = "Department name cannot be blank")
    @Size(max = 50, message = "Department name must be less than 50 characters")
    private String departmentName;

    @NotBlank(message = "Department description cannot be blank")
    @Size(max = 255, message = "Department description must be less than 255 characters")
    private String departmentDescription;

    //relations
    private List<DoctorDTO> doctors = new ArrayList<>();
}
