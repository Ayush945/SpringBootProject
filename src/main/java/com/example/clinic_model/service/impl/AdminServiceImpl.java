package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.AdminDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Admin;
import com.example.clinic_model.repository.AdminRepository;
import com.example.clinic_model.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) {
        Optional<Admin> savedAdmin = adminRepository.findById(adminId);

        if (savedAdmin.isEmpty()) {
            throw new ResourceNotFoundException("Admin not found");
        }

        Admin existingAdmin = savedAdmin.get();

        // Update properties from DTO
        if (adminDTO.getAdminName() != null) {
            existingAdmin.setAdminName(adminDTO.getAdminName());
        }
        if (adminDTO.getPhone() != null) {
            existingAdmin.setPhone(adminDTO.getPhone());
        }
        if (adminDTO.getEmail() != null) {
            existingAdmin.setEmail(adminDTO.getEmail());
        }
        if(adminDTO.getJoinDate()!=null){
            existingAdmin.setJoinDate((adminDTO.getJoinDate()));
        }


        Admin updatedAdmin = adminRepository.save(existingAdmin);

        return modelMapper.map(updatedAdmin, AdminDTO.class);
    }
}
