package com.Library_Management_System.author;

import com.Library_Management_System.author.dto.AddAuthorDto;
import com.Library_Management_System.author.dto.AuthorDto;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface AuthorService {

    AuthorDto addAuthor(AuthorDto dto);

    UpdateResult updateAuthor(AuthorDto dto, String authorId);

    DeleteResult delete(String authorId);

    List<AuthorDto> retrieveAllAuthors();

    AuthorDto getAuthorDetails(String authorId);

    Boolean exists(String authorId);}
