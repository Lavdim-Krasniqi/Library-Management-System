package com.Library_Management_System.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@AllArgsConstructor
public class AuthorReference {

    @Field(targetType = FieldType.OBJECT_ID)
    private String authorId;
    private String fullName;
}
