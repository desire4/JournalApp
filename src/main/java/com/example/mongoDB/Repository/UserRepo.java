package com.example.mongoDB.Repository;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,ObjectId>{
    User findByUserName(String username);

    User deleteByUserName(String username);
}
