package com.Library_Management_System.author.converter;

import com.Library_Management_System.author.dto.AddAuthorDto;
import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.author.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorConverter {

    public AuthorDto entityToAuthorDto(Author author) {
        AuthorDto toReturn = new AuthorDto();
        if (author != null) {
            toReturn.setId(author.getId());
            toReturn.setName(author.getName());
            toReturn.setSurname(author.getSurname());
            toReturn.setBirthdate(author.getBirthdate());
            toReturn.setCountry(author.getCountry());
            return toReturn;
        }
        return null;
    }

    public Author authorDtoToEntity(AuthorDto dto) {
        Author entity = new Author();
        if (dto != null) {
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setBirthdate(dto.getBirthdate());
            entity.setCountry(dto.getCountry());
            return entity;
        }
        return null;
    }

    public List<AuthorDto> toListOfAuthorDto(List<Author> authors) {
        return authors.stream().map(this::entityToAuthorDto).toList();
    }

}
