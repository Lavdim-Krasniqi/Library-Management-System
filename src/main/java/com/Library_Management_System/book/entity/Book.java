package com.Library_Management_System.book.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("Book")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "book_index", def = "{'author.authorId': 1, 'title': 1}", unique = true)
}
)
public class Book {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String ISBN = "isbn";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR_ID = "author.authorId";
    public static final String AUTHOR_FULL_NAME = "author.fullName";
    public static final String AUTHOR = "author";

    @Id
    private String id;
    private String title;
    private String isbn;
    private String description;
    private AuthorReference author;
    @CreatedDate
    public Instant createdAt;
    @LastModifiedDate
    public Instant modifiedAt;

}
