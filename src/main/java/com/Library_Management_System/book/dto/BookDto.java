package com.Library_Management_System.book.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BookDto {

    private String id;
    private String title;
    private String isbn;
    private String description;
    private String authorFullName;
    private String authorId;
}
