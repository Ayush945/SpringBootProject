package com.example.clinic_model.repository;

import com.example.clinic_model.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>
{
    Optional<Admin> findByUsername(String username);
}
