package com.example.mongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class MongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDbApplication.class, args);
	}

	//in main we can do configuration because @springbootApplication allow configuration in this clss

	//here for transaction use case for applying commit and rollback operation on db we need
	//	the instance of PlatformTransactionManager and its interface so we will use MongoTransactionManager
	//	which will return the instance of PlatformTransactionManager

	//moved this code to the TransactionConfig class for clearity
//	@Bean
//	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
//		//MongoDatabase Factory helps to connect with db
//		return new MongoTransactionManager(dbFactory);
//	}


}
