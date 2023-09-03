package com.example.clinic_model;

import com.example.clinic_model.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicModelApplication implements CommandLineRunner{
	@Autowired
	private AuthenticationService authenticationService;

	public static void main(String[] args)
	{
		SpringApplication.run(ClinicModelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		this.authenticationService.createAdmin();
	}
}
