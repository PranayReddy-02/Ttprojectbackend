package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/demo")
    String demo(){
        return "I am good how are you..";
    }

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return (List<Users>) this.userRepo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody Users user)
    {
        Users u=this.userRepo.findByEmail((String) user.getEmail());
        if(u!=null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        this.userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

    }
    
    
}
