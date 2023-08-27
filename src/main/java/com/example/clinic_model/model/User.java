package com.example.clinic_model.model;

import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate joinDate;
}