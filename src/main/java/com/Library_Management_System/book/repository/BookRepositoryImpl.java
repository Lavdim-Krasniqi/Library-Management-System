package com.Library_Management_System.book.repository;

import com.Library_Management_System.book.entity.Book;
import com.mongodb.client.result.DeleteResult;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final MongoTemplate mongoTemplate;

    public BookRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Book addBook(Book book) {
        return mongoTemplate.save(book);
    }

    @Override
    public Book update(Book book) {
        return mongoTemplate.save(book);
    }

    @Override
    public DeleteResult delete(String bookId) {
        val c1 = Criteria.where(Book.ID).is(bookId);
        return mongoTemplate.remove(Query.query(c1), Book.class);
    }

    @Override
    public List<Book> getAllBooks() {
        return mongoTemplate.findAll(Book.class);
    }

    @Override
    public Book getBookDetails(String bookId) {
        return mongoTemplate.findOne(Query.query(Criteria.where(Book.ID).is(bookId)), Book.class);
    }

    @Override
    public DeleteResult deleteBooksByAuthorId(String authorId) {
        return mongoTemplate.remove(Query.query(Criteria.where(Book.AUTHOR_ID).is(authorId)), Book.class);
    }
}
