package com.Library_Management_System.book.service;

import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.author.AuthorService;
import com.Library_Management_System.book.converter.BookConverter;
import com.Library_Management_System.book.dto.BookDto;
import com.Library_Management_System.book.entity.AuthorReference;
import com.Library_Management_System.book.entity.Book;
import com.Library_Management_System.book.repository.BookRepository;
import com.Library_Management_System.configuration.exception.BadRequestException;
import com.Library_Management_System.configuration.exception.NotFoundException;
import com.mongodb.client.result.DeleteResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final AuthorService authorService;
    private final BookConverter bookConverter;

    public BookServiceImpl(BookRepository repository, AuthorService authorService, BookConverter bookConverter) {
        this.repository = repository;
        this.authorService = authorService;
        this.bookConverter = bookConverter;
    }


    @Override
    public BookDto addBook(BookDto dto) {
        if (dto == null) throw new BadRequestException("Wrong request body!");
        AuthorDto authorDetails = authorService.getAuthorDetails(dto.getAuthorId());
        if (authorDetails != null) {
            Book entity = bookConverter.toEntity(dto);
            entity.setAuthor(new AuthorReference(dto.getAuthorId(), authorDetails.name + " " + authorDetails.getSurname()));
            return bookConverter.toDto(repository.addBook(entity));
        }

        throw new NotFoundException("Author does not exists!");
    }

    @Override
    public BookDto update(BookDto dto) {
        if (dto != null) {
            Book entity = repository.getBookDetails(dto.getId());
            if (dto.getAuthorId() != null) {
                AuthorDto authorDetails = authorService.getAuthorDetails(dto.getAuthorId());
                if (authorDetails != null) {
                    entity.setAuthor(new AuthorReference(dto.getAuthorId(), authorDetails.getName() + " " + authorDetails.getSurname()));
                } else throw new NotFoundException("Author does not exists!");
            }

            if (dto.getIsbn() != null) entity.setIsbn(dto.getIsbn());
            if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
            if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
            return bookConverter.toDto(repository.update(entity));
        }

        throw new BadRequestException("Wrong request body!");
    }

    @Override
    public DeleteResult delete(String bookId) {
        return repository.delete(bookId);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookConverter.toList(repository.getAllBooks());
    }

    @Override
    public BookDto getBookDetails(String bookId) {
        return bookConverter.toDto(repository.getBookDetails(bookId));
    }

    @Override
    public DeleteResult deleteBooksByAuthorId(String authorId) {
        return repository.deleteBooksByAuthorId(authorId);
    }
}
