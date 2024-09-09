package com.Library_Management_System.book.repository;

import com.Library_Management_System.book.entity.Book;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface BookRepository {

    Book addBook(Book book);

    Book update(Book book);

    DeleteResult delete(String bookId);

    List<Book> getAllBooks();

    Book getBookDetails(String bookId);

    DeleteResult deleteBooksByAuthorId(String authorId);

}

