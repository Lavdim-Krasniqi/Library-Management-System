package com.Library_Management_System.user.repository;

import com.Library_Management_System.user.entity.Token;
import com.Library_Management_System.user.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl {

    private MongoTemplate repo;


    public User addUser(User user) {
        return repo.insert(user);
    }

    public User findByUsername(String username) {
        val c1 = Criteria.where(User.USERNAME).is(username);
        return repo.findOne(Query.query(c1), User.class);
    }

    public DeleteResult deleteUserByUsername(String username) {
        val c1 = Criteria.where(User.USERNAME).is(username);
        return repo.remove(Query.query(c1), User.class);
    }

    public boolean doesExists(String username) {
        val c1 = Criteria.where(User.USERNAME).is(username);
        return repo.exists(Query.query(c1), User.class);
    }

    public UpdateResult revokeToken(String userId, String token) {
        val c1 = Criteria.where(User.ID).is(userId);
        val c2 = Criteria.where(User.ACCESS_TOKEN).is(token);
        val update = new Update();
        update.set(User.TOKEN_IS_REVOKED, Boolean.TRUE);

        return repo.updateFirst(Query.query(new Criteria().andOperator(c1, c2)), update, User.class);
    }

    public UpdateResult addToken(Token token, String username) {
        val c1 = Criteria.where(User.USERNAME).is(username);
        val update = new Update();
        update.set(User.TOKEN, token);
        return repo.updateFirst(Query.query(c1), update, User.class);
    }

    public User findUserByAccessToken(String accessToken) {
        return repo.findOne(Query.query(Criteria.where(User.ACCESS_TOKEN).is(accessToken)), User.class);
    }

}
