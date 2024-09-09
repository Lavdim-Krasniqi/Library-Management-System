package com.Library_Management_System.author.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Document("Author")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "author_index", def = "{'name': 1, 'surname': 1, 'birthdate': 1}", unique = true)})
public class Author {

    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String BIRTHDATE = "birthdate";
    public final static String COUNTRY = "country";

    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String country;
    @CreatedDate
    public Instant createdAt;
    @LastModifiedDate
    public Instant modifiedAt;
}
