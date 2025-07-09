package com.example.mongoDB.service;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.Entity.User;
import com.example.mongoDB.Repository.JournalRepo;
import com.example.mongoDB.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
	
	@Autowired
	UserRepo userRepo;

	private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

//	instead of writing this logger statement we can use slf4j anotation
//	private	static final Logger logger = LoggerFactory.getLogger(UserService.class);


	public void saveEntry(User user) {
		try{
			userRepo.save(user);
		} catch (Exception e) {
			log.error("Error occured",e);
			log.warn("Hahahaha");
			log.info("Hahahaha");
			log.debug("Hahahaha");
			log.trace("Hahahaha");
		}
	}

	public void saveNewUser(User user){
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepo.save(user);
		}catch (Exception e){
			log.error("Error occured",e);
			log.warn("Hahahaha");
			log.info("Hahahaha");
			log.debug("Hahahaha");
			log.trace("Hahahaha");
			log.trace("Hahahaha");
		}
	}

	public void saveAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepo.save(user);
	}
	
	public ResponseEntity<?> getAll(){

		List<User> users= userRepo.findAll();
		if(!users.isEmpty()){
			return ResponseEntity.status(HttpStatus.OK).body(users);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
	}

	public Optional<User> findById(ObjectId myId) {
		
		return userRepo.findById(myId);
	}

	public void deleteByUsername(String username ) {
		userRepo.deleteByUserName(username);

	}



	public User findByUserName(String username){
		return userRepo.findByUserName(username);
	}



}
