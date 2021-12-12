package com.example.projekt.controller;

import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.model.Auth;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static com.example.projekt.util.crypt.getSHA256;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private Exceptionhandler exHandler;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping
    public @ResponseBody String authorize(){
        return "Use POST to create a new User, PUT to change an existing one and DELETE to delete one";
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
            return JwtUtil.generateToken(new Auth(user.getEmail(), user.getPassword())) + "\n" + temp;
        }
        return "registration failed";
        //return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") User user){
        userRepository.delete(user);
        return user;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return exHandler.handleGeneralExceptions(ex);
    }
}
