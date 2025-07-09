package com.example.mongoDB.Controller;

import com.example.mongoDB.Entity.User;
import com.example.mongoDB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> creatUser(@RequestBody User user){

            userService.saveNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");

    }
}
