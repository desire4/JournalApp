package com.example.mongoDB.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mongoDB.Entity.JournalEntry;

public interface JournalRepo extends MongoRepository<JournalEntry,ObjectId>{

}
