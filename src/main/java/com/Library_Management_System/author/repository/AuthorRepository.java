package com.Library_Management_System.author.repository;

import com.Library_Management_System.author.entity.Author;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface AuthorRepository {

    Author addAuthor(Author author);

    UpdateResult updateAuthor(Author author, String authorId);

    DeleteResult delete(String authorId);

    List<Author> retrieveAllAuthors();

    Author getAuthorDetails(String authorId);

    Boolean exists(String authorId);

}
