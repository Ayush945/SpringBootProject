package com.example.clinic_model.service;

import com.example.clinic_model.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(Long departmentId);

    DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO departmentDTO);
    void deleteDepartmentById(Long departmentId);

    void assignDoctorToDepartment(Long departmentId, Long doctorId);
}
