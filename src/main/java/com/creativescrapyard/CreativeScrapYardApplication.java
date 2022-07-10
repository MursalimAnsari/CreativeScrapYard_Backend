package com.creativescrapyard;

import com.creativescrapyard.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CreativeScrapYardApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CreativeScrapYardApplication.class, args);
	}

	@Override 
	public void run(String... args) throws Exception {
		System.out.println("Hey go on !!!");
		//System.out.println(this.passwordEncoder.encode("mursu03"));

	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


}
