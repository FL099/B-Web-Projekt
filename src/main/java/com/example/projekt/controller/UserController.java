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

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("/{mail}")
    public @ResponseBody Boolean isEmailAvailable(@PathVariable("mail") String email){
        if(!userRepository.findByEmailContaining(email).isEmpty()){
            return true;
        }
        return false;
        //TODO: falls besser: false wenns nicht verfügbar ist, true wenn schon
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody @Valid User user){
        String temp ;
        if (user != null){

            //Hash zum speichern des PW in der Datenbank
            temp = getSHA256(user.getPassword());
            user.setPassword(temp);
            try{
                userRepository.save(user);
            }catch (Exception e){   //TODO: nur eigentliche Exception behandeln: java.sql.SQLIntegrityConstraintViolationException
                return "Erstellen fehlgeschlagen";
            }

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
        return Exceptionhandler.handleGeneralExceptions(ex);
    }
}
