package com.example.mongoDB.Repository;


import com.example.mongoDB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();

        //for or operator
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//                criteria.where("email").exists(true),
//                criteria.where("sentimetAnalysis").is(true)));



//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));


        return mongoTemplate.find(query, User.class);
    }
}
