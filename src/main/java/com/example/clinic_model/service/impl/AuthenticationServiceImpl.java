package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.*;
import com.example.clinic_model.exception.LoginException;
import com.example.clinic_model.model.Admin;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.model.enums.RoleEnum;
import com.example.clinic_model.repository.AdminRepository;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.repository.PatientRepository;
import com.example.clinic_model.service.AuthenticationService;
import com.example.clinic_model.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JavaMailSender mailSender;


    //    @Override
//    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
//        LoginResponseDTO loginResponseDTO;
//        if (loginRequestDTO.getRole().equals(RoleEnum.ROLE_ADMIN)) {
//            loginResponseDTO = this.loginAsAdmin(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
//        } else if (loginRequestDTO.getRole().equals(RoleEnum.ROLE_DOCTOR)) {
//            loginResponseDTO = this.loginAsDoctor(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
//        } else {
//            loginResponseDTO = this.loginAsPatient(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
//        }
//        return loginResponseDTO;
//    }
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        // Check if the username exists in any of the user repositories
        Optional<Admin> admin = adminRepository.findByUsername(username);
        Optional<Patient> patient = patientRepository.findByUsername(username);
        Optional<Doctor> doctor = doctorRepository.findByUsername(username);

        if (patient.isPresent()) {
            Patient patientEntity = patient.get();
            if (!patientEntity.isVerified()) {
                throw new LoginException("OTP NOT VERIFIED");
            }
            return loginAsPatient(username, password);
        } else if (admin.isPresent()) {
            return loginAsAdmin(username, password);
        } else if (doctor.isPresent()) {
            return loginAsDoctor(username, password);
        } else {
            throw new LoginException("User not found");
        }
    }


//@Override
//public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
//    LoginResponseDTO loginResponseDTO;
//        loginResponseDTO = this.loginAsAdmin(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
//    return loginResponseDTO;
//}

    @Override
    public void createAdmin() {
        LocalDate currentDate =  LocalDate.now();
        Admin admin = new Admin();
        admin.setAdminName("Diwon Sigdel");
        admin.setPassword(this.passwordEncoder.encode("admin"));
        admin.setEmail("admin@gmail.com");
        admin.setPhone("+977 9803209987");
        admin.setUsername("admin");
        admin.setJoinDate(currentDate);
        this.adminRepository.save(admin);
    }


    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String username, RoleEnum role) {
        if (role.equals(RoleEnum.ROLE_PATIENT)) {
            PatientDTO patientDTO = this.getPatientByUsername(username);
            List<SimpleGrantedAuthority> authorities = this.addAuthority(patientDTO.getRole());
            return new UsernamePasswordAuthenticationToken(patientDTO.getUsername(), patientDTO.getPassword(),
                    authorities);
        } else if (role.equals(RoleEnum.ROLE_DOCTOR)) {
            DoctorDTO doctorDTO = this.getDoctorByUsername(username);
            List<SimpleGrantedAuthority> authorities = this.addAuthority(doctorDTO.getRole());
            return new UsernamePasswordAuthenticationToken(doctorDTO.getUsername(), doctorDTO.getPassword(),
                    authorities);
        } else {
            AdminDTO adminDTO = this.getAdminByUsername(username);
            List<SimpleGrantedAuthority> authorities = this.addAuthority(adminDTO.getRole());
            return new UsernamePasswordAuthenticationToken(adminDTO.getUsername(), adminDTO.getPassword(),
                    authorities);
        }
    }

    //    Admin Login
    private LoginResponseDTO loginAsAdmin(String username, String password) {
        AdminDTO adminDTO = this.getAdminByUsername(username);
        return this.authenticate(adminDTO, password, adminDTO.getRole());
    }

    private AdminDTO getAdminByUsername(String username) {
        Admin admin = this.adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        return modelMapper.map(admin, AdminDTO.class);
    }


    //    Patient Login
    private LoginResponseDTO loginAsPatient(String username, String password) {
        PatientDTO patientDTO = this.getPatientByUsername(username);
        return this.authenticate(patientDTO, password, patientDTO.getRole());
    }

    private PatientDTO getPatientByUsername(String username) {
        Patient patient = this.patientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
        return modelMapper.map(patient, PatientDTO.class);
    }

    //    Doctor Login
    private LoginResponseDTO loginAsDoctor(String username, String password) {
        DoctorDTO doctorDTO = this.getDoctorByUsername(username);
        return this.authenticate(doctorDTO, password, doctorDTO.getRole());
    }

    private DoctorDTO getDoctorByUsername(String username) {
        Doctor doctor = this.doctorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
        return modelMapper.map(doctor, DoctorDTO.class);
    }

//    Register Student

    @Override
    public PatientDTO registerAsPatient(PatientDTO patientDTO) {
        LocalDate currentDate =  LocalDate.now();
        Patient patient = this.modelMapper.map(patientDTO, Patient.class);
        if (this.userNameExists(patient.getUsername())) {
            log.error("Username {} already taken", patient.getUsername());
            throw new LoginException("Username already taken");
        }

        String patientEmail=patient.getEmail();
        int patientOtp=generateUniqueOTP();
        patient.setOtp(patientOtp);
        sendOTP(patientEmail,patientOtp);
        patient.setJoinDate(currentDate);

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        this.patientRepository.save(patient);
        patient.setPassword(null);
        return this.modelMapper.map(patient, PatientDTO.class);
    }

    //    Register Doctor
    @Override
    public DoctorDTO registerAsDoctor(DoctorDTO doctorDTO) {
        LocalDate currentDate =  LocalDate.now();
        Doctor doctor = this.modelMapper.map(doctorDTO, Doctor.class);
        if (this.userNameExists(doctor.getUsername())) {
            log.error("Username {} already taken", doctor.getUsername());
            throw new LoginException("Username already taken");
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setJoinDate(currentDate);
        this.doctorRepository.save(doctor);
        doctor.setPassword(null);
        return this.modelMapper.map(doctor, DoctorDTO.class);
    }


    private LoginResponseDTO authenticate(UserDTO userDTO, String rawPassword, RoleEnum role) throws UsernameNotFoundException {
        this.checkPassword(rawPassword, userDTO.getPassword());
        List<SimpleGrantedAuthority> authorities = this.addAuthority(role);
        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(),
                authorities);
        String accessToken = this.jwtUtil.generateToken(
                usernamePasswordAuthenticationToken.getName(),
                this.generateRoles(usernamePasswordAuthenticationToken.getAuthorities())
        );
        userDTO.setPassword(null);
        return new LoginResponseDTO(accessToken, userDTO);
    }

    private List<SimpleGrantedAuthority> addAuthority(RoleEnum roleEnum) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (Objects.nonNull(roleEnum)) {
            authorities.add(new SimpleGrantedAuthority(roleEnum.toString()));
        }
        return authorities;
    }

    private void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new BadCredentialsException("Password Incorrect");
    }

    private List<String> generateRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = new ArrayList<>();
        authorities.forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));
        return roles;
    }

    private Boolean userNameExists(String username) {
        Optional<Admin> savedAdmin = this.adminRepository.findByUsername(username);
        Optional<Patient> savedPatient = this.patientRepository.findByUsername(username);
        Optional<Doctor> savedDoctor = this.doctorRepository.findByUsername(username);
        return savedAdmin.isPresent() || savedPatient.isPresent() || savedDoctor.isPresent();
    }

    //OTP Addition
    private int generateUniqueOTP() {
        Random random = new Random();
        int Otp;
        boolean isUnique = false;
        do {
            Otp = random.nextInt(9000) + 1000;
            boolean isExisting = patientRepository.existsByOtp(Otp);
            isUnique = !isExisting;
        } while (!isUnique);
        return Otp;
    }

    private void sendOTP(String email, int otp) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("sigdeldiwon@gmail.com");
        message.setTo(email);
        message.setText("Your Otp is: "+otp);
        message.setSubject("All Well Registration");
        mailSender.send(message);
        System.out.println("Mail Sent.....");
    }

    public void verifyOTP(int otp) {
        Optional<Patient> patientGet= this.patientRepository.findByOtp(otp);
        Patient otpPatient =patientGet.get();
        otpPatient.setVerified(true);
        otpPatient.setOtp(0);
        patientRepository.save(otpPatient);
    }
}

