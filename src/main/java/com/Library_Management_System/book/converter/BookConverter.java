package com.Library_Management_System.book.converter;

import com.Library_Management_System.book.dto.BookDto;
import com.Library_Management_System.book.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookConverter {

    public Book toEntity(BookDto dto) {
        Book book = new Book();
        if (dto != null) {
            book.setTitle(dto.getTitle());
            book.setIsbn(dto.getIsbn());
            book.setDescription(dto.getDescription());
            return book;
        }
        return null;
    }

    public BookDto toDto(Book book) {
        BookDto toReturn = new BookDto();
        if (book != null) {
            toReturn.setId(book.getId());
            toReturn.setTitle(book.getTitle());
            toReturn.setDescription(book.getDescription());
            toReturn.setIsbn(book.getIsbn());
            toReturn.setAuthorFullName(book.getAuthor().getFullName());
            toReturn.setAuthorId(book.getAuthor().getAuthorId());
            return toReturn;
        }
        return null;
    }

    public List<BookDto> toList(List<Book> books) {
        return books.stream().map(this::toDto).toList();
    }
}
