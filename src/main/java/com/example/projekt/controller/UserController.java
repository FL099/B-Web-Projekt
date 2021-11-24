package com.example.projekt.controller;

import com.example.projekt.model.Product;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping
    public @ResponseBody String authorize(){
        return "Hi";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") User user){
        userRepository.delete(user);
        return user;
    }
}
