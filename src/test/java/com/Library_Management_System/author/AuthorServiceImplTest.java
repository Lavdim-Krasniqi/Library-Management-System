package com.Library_Management_System.author;

import com.Library_Management_System.author.converter.AuthorConverter;
import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.author.entity.Author;
import com.Library_Management_System.author.repository.AuthorRepository;
import com.Library_Management_System.book.service.BookService;
import com.Library_Management_System.configuration.exception.BadRequestException;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorConverter converter;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAuthorSuccess() {

        AuthorDto dto = getAuthorDto();
        Author author = getAuthor();

        when(converter.authorDtoToEntity(dto)).thenReturn(author);
        when(authorRepository.addAuthor(author)).thenReturn(author);
        when(converter.entityToAuthorDto(author)).thenReturn(dto);


        AuthorDto result = authorService.addAuthor(dto);


        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("lavdim");
        verify(authorRepository, times(1)).addAuthor(any(Author.class));
    }

    @Test
    void addAuthorThrowsBadRequestExceptionForNullFields() {

        AuthorDto dto = new AuthorDto(); // Empty dto
        when(converter.authorDtoToEntity(dto)).thenReturn(null);


        assertThrows(BadRequestException.class, () -> authorService.addAuthor(dto));
        verify(authorRepository, never()).addAuthor(any(Author.class));
    }

    @Test
    void updateAuthorSuccess() {

        AuthorDto dto = getAuthorDto();
        Author author = getAuthor();
        UpdateResult updateResult = mock(UpdateResult.class);

        when(converter.authorDtoToEntity(dto)).thenReturn(author);
        when(authorRepository.updateAuthor(author, "1")).thenReturn(updateResult);


        UpdateResult result = authorService.updateAuthor(dto, "1");


        assertThat(result).isNotNull();
        verify(authorRepository, times(1)).updateAuthor(any(Author.class), eq("1"));
    }

    @Test
    void updateAuthorThrowsBadRequestException() {

        assertThrows(BadRequestException.class, () -> authorService.updateAuthor(null, "1"));
        verify(authorRepository, never()).updateAuthor(any(Author.class), anyString());
    }

    public AuthorDto getAuthorDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("lavdim");
        authorDto.setSurname("Krasniqi");
        authorDto.setCountry("Kosova");
        authorDto.setBirthdate(LocalDate.of(1999, 4, 19));
        return authorDto;
    }

    public Author getAuthor() {
        Author author = new Author();
        author.setId("1");
        author.setName("lavdim");
        author.setSurname("krasniqi");
        author.setCountry("Kosova");
        author.setBirthdate(LocalDate.of(1999, 4, 19));
        return author;
    }
}
