package com.Library_Management_System;

import org.springframework.boot.SpringApplication;

public class TestLibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(LibraryManagementSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
