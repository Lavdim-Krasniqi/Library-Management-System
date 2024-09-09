package com.Library_Management_System.author.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddAuthorDto {

    public String name;
    public String surname;
    public LocalDate birthdate;
    public String country;
}
