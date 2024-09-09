package com.Library_Management_System.book;

import com.Library_Management_System.author.AuthorService;
import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.book.converter.BookConverter;
import com.Library_Management_System.book.dto.BookDto;
import com.Library_Management_System.book.entity.AuthorReference;
import com.Library_Management_System.book.entity.Book;
import com.Library_Management_System.book.repository.BookRepository;
import com.Library_Management_System.book.service.BookServiceImpl;
import com.Library_Management_System.configuration.exception.BadRequestException;
import com.Library_Management_System.configuration.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @Mock
    private AuthorService authorService;

    @Mock
    private BookConverter bookConverter;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDto bookDto;
    private Book bookEntity;
    private AuthorDto authorDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookDto = new BookDto();
        bookDto.setId("1");
        bookDto.setTitle("Test Book");
        bookDto.setIsbn("123456789");
        bookDto.setDescription("Test Description");
        bookDto.setAuthorId("author1");
        bookDto.setAuthorFullName("Author Name");

        authorDto = new AuthorDto();
        authorDto.setId("author1");
        authorDto.setName("lavdim");
        authorDto.setSurname("krasniqi");
        authorDto.setCountry("Kosova");

        AuthorReference authorReference = new AuthorReference("author1", "Author Name");

        bookEntity = new Book();
        bookEntity.setId("1");
        bookEntity.setTitle("Test Book");
        bookEntity.setIsbn("123456789");
        bookEntity.setDescription("Test Description");
        bookEntity.setAuthor(authorReference);
    }

    @Test
    void testAddBook_Success() {

        when(authorService.getAuthorDetails("author1")).thenReturn(authorDto);
        when(bookConverter.toEntity(bookDto)).thenReturn(bookEntity);
        when(repository.addBook(bookEntity)).thenReturn(bookEntity);
        when(bookConverter.toDto(bookEntity)).thenReturn(bookDto);


        BookDto result = bookService.addBook(bookDto);


        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(repository).addBook(any(Book.class));
    }

    @Test
    void testAddBook_AuthorNotFound() {

        when(authorService.getAuthorDetails("author1")).thenReturn(null);


        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.addBook(bookDto));
        assertEquals("Author does not exists!", exception.getMessage());
    }

    @Test
    void testAddBook_BadRequest() {

        BadRequestException exception = assertThrows(BadRequestException.class, () -> bookService.addBook(null));
        assertEquals("Wrong request body!", exception.getMessage());
    }

    @Test
    void testUpdate_Success() {

        when(repository.getBookDetails("1")).thenReturn(bookEntity);
        when(authorService.getAuthorDetails("author1")).thenReturn(authorDto);
        when(bookConverter.toDto(bookEntity)).thenReturn(bookDto);
        when(repository.update(bookEntity)).thenReturn(bookEntity);


        BookDto result = bookService.update(bookDto);


        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(repository).update(any(Book.class));
    }

    @Test
    void testUpdate_AuthorNotFound() {

        when(repository.getBookDetails("1")).thenReturn(bookEntity);
        when(authorService.getAuthorDetails("author1")).thenReturn(null);


        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.update(bookDto));
        assertEquals("Author does not exists!", exception.getMessage());
    }

    @Test
    void testUpdate_BadRequest() {

        BadRequestException exception = assertThrows(BadRequestException.class, () -> bookService.update(null));
        assertEquals("Wrong request body!", exception.getMessage());
    }
}
