package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.DepartmentDTO;
import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Department;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.repository.DepartmentRepository;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.service.DepartmentService;
import com.example.clinic_model.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        department = departmentRepository.save(department);
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO departmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

        if (optionalDepartment.isEmpty()) {
            throw new ResourceNotFoundException("Department not found");
        }

        Department existingDepartment = optionalDepartment.get();

        // Update properties from DTO
        if (departmentDTO.getDepartmentName() != null) {
            existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        }
        if (departmentDTO.getDepartmentDescription() != null) {
            existingDepartment.setDepartmentDescription(departmentDTO.getDepartmentDescription());
        }

        // Save the updated department
        Department updatedDepartment = departmentRepository.save(existingDepartment);

        return modelMapper.map(updatedDepartment, DepartmentDTO.class);
    }


    @Override
    public void deleteDepartmentById(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department not found");
        }
        departmentRepository.deleteById(departmentId);
    }


    @Override
    public void assignDoctorToDepartment(Long departmentId, Long doctorId) {
        DepartmentDTO departmentDTO = getDepartmentById(departmentId);
        Department department = this.modelMapper.map(departmentDTO, Department.class);

        DoctorDTO doctorDTO = this.doctorService.getDoctorById(doctorId);
        Doctor doctor = this.modelMapper.map(doctorDTO, Doctor.class);

        department.getDoctors().add(doctor);
        doctor.getDepartments().add(department);

        this.doctorRepository.save(doctor);
        this.departmentRepository.save(department);
    }

}

