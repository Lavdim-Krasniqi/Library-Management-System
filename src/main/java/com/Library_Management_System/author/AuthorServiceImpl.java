package com.Library_Management_System.author;

import com.Library_Management_System.author.converter.AuthorConverter;
import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.author.entity.Author;
import com.Library_Management_System.author.repository.AuthorRepository;
import com.Library_Management_System.book.service.BookService;
import com.Library_Management_System.configuration.exception.BadRequestException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final AuthorConverter authorConverter;

    public AuthorServiceImpl(AuthorRepository authorRepository, @Lazy BookService bookService, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
        this.authorConverter = authorConverter;
    }

    @Override
    public AuthorDto addAuthor(AuthorDto dto) {
        Author author = authorConverter.authorDtoToEntity(dto);

        if (author == null) throw new BadRequestException("Bad request!");

        if (author.getName() == null || author.getSurname() == null || author.getBirthdate() == null) {
            throw new BadRequestException("Fields name, surname and birthdate must not be empty!");
        }

        return authorConverter.entityToAuthorDto(authorRepository.addAuthor(author));
    }

    @Override
    public UpdateResult updateAuthor(AuthorDto dto, String authorId) {
        if (dto != null) {
            return authorRepository.updateAuthor(authorConverter.authorDtoToEntity(dto), authorId);
        } else throw new BadRequestException("Wrong request body!");

    }

    @Override
    public DeleteResult delete(String authorId) {
        bookService.deleteBooksByAuthorId(authorId);
        return authorRepository.delete(authorId);
    }

    @Override
    public List<AuthorDto> retrieveAllAuthors() {
        return authorConverter.toListOfAuthorDto(authorRepository.retrieveAllAuthors());
    }

    @Override
    public AuthorDto getAuthorDetails(String authorId) {
        return authorConverter.entityToAuthorDto(authorRepository.getAuthorDetails(authorId));
    }

    @Override
    public Boolean exists(String authorId) {
        return authorRepository.exists(authorId);
    }

}
