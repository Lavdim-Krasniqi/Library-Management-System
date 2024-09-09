package com.Library_Management_System.author.repository;

import com.Library_Management_System.author.entity.Author;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Author addAuthor(Author author) {
        return mongoTemplate.save(author);
    }

    @Override
    public UpdateResult updateAuthor(Author author, String authorId) {
        val c1 = Criteria.where(Author.ID).is(authorId);
        Update update = new Update();
        if (author.getName() != null) update.set(Author.NAME, author.getName());
        if (author.getSurname() != null) update.set(Author.SURNAME, author.getSurname());
        if (author.getBirthdate() != null) update.set(Author.BIRTHDATE, author.getBirthdate());
        if (author.getCountry() != null) update.set(Author.COUNTRY, author.getCountry());

        return mongoTemplate.updateFirst(Query.query(c1), update, Author.class);
    }

    @Override
    public DeleteResult delete(String authorId) {
        val c1 = Criteria.where(Author.ID).is(authorId);
        return mongoTemplate.remove(Query.query(c1), Author.class);
    }

    @Override
    public List<Author> retrieveAllAuthors() {
        return mongoTemplate.findAll(Author.class);
    }

    @Override
    public Author getAuthorDetails(String authorId) {
        return mongoTemplate.findOne(Query.query(Criteria.where(Author.ID).is(authorId)), Author.class);
    }

    @Override
    public Boolean exists(String authorId) {
        return mongoTemplate.exists(Query.query(Criteria.where(Author.ID).is(authorId)), Author.class);
    }
}
