package com.Library_Management_System.book.service;

import com.Library_Management_System.book.dto.BookDto;
import com.mongodb.client.result.DeleteResult;

import java.util.List;

public interface BookService {

    BookDto addBook(BookDto book);

    BookDto update(BookDto book);

    DeleteResult delete(String bookId);

    List<BookDto> getAllBooks();

    BookDto getBookDetails(String bookId);

    DeleteResult deleteBooksByAuthorId(String authorId);
}
