package com.example.mongoDB.Controller;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.Entity.User;
import com.example.mongoDB.service.JournalService;
import com.example.mongoDB.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping
//	public ResponseEntity<?> getAllUsers(){
//		return userService.getAll();
//	}

//	@PostMapping
//	public ResponseEntity<?> creatUser(@RequestBody User user){
//		try{
//			userService.saveNewUser(user);
//			return ResponseEntity.status(HttpStatus.CREATED).body("Created");
//		}catch (Exception e){
//			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
//		}
//	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user){
		//we normall write logic into service class but for temporaray
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username =  authentication.getName();
		User userInDb = userService.findByUserName(username);
//		if(userInDb != null){
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveNewUser(userInDb);
//		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(){
		//we normally do this in service but for spped
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userService.deleteByUsername(authentication.getName());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
