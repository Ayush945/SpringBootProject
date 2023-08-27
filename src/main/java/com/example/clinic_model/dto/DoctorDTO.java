package com.example.clinic_model.dto;

import com.example.clinic_model.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorDTO extends UserDTO{
    private Long doctorId;

    @NotBlank(message = "Doctor name cannot be blank")
    @Size(max = 100, message = "Doctor name must be less than 100 characters")
    private String doctorName;

    @NotBlank(message = "Specialization cannot be blank")
    @Size(max = 100, message = "Specialization must be less than 100 characters")
    private String specialization;

    private final RoleEnum role = RoleEnum.ROLE_DOCTOR;

    @NotNull(message = "Doctor license number is required")
    private Long doctorLicense;


    //relations
//    @JsonBackReference
//    private List<AppointmentDTO> appointments;

    @JsonBackReference
    private List<DepartmentDTO> departments = new ArrayList<>();
}
