package com.Library_Management_System.author.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDto {

    public String id;
    public String name;
    public String surname;
    public LocalDate birthdate;
    public String country;
}
