package com.example.mongoDB.Controller;

import com.example.mongoDB.Entity.User;
import com.example.mongoDB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/create-admin-user")
    public  ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>("Admin created", HttpStatus.CREATED);
    }
}
