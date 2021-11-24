package com.example.projekt.controller;

import com.example.projekt.model.Product;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.projekt.util.crypt.getSHA256;

@RestController
@RequestMapping("/user")
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
    public String createUser(@RequestBody @Valid User user){
        String temp ;
        if (user != null){
            //Hash zum speichern des PW in der Datenbank
            temp = getSHA256(user.getPassword());
            user.setPassword(temp);
            userRepository.save(user);
            //JWT Token generieren
            return JwtUtil.generateToken(user.getEmail());
            //return temp;
            //return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ik1heCBNdXN0ZXJtYW5uIiwiaWF0IjoxNjM4MDM5MDIyfQ.tjA107F7gW21ImFN0XHTxPgruG2iNqr-8z99byBjji0";
        }
        return "registration failed";
        //return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") User user){
        userRepository.delete(user);
        return user;
    }
}
