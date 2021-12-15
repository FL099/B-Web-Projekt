package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.model.Auth;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static com.example.projekt.util.crypt.getSHA256;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ModelMapper modelMapper = new ModelMapper();

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/mail/{mail}")
    public @ResponseBody Boolean isEmailAvailable(@PathVariable("mail") String email){
        if(!userRepository.findByEmailContaining(email).isEmpty()){
            return true;
        }
        return false;
        //TODO: falls besser: false wenns nicht verfügbar ist, true wenn schon
    }

    @GetMapping
    public Iterable<UserDto> getUsers() {
        return modelMapper.map(userRepository.findAll(),
                new TypeToken<List<UserDto>>(){}.getType());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return modelMapper.map(getUserFromRepoOrThrow(id), UserDto.class);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody @Valid UserDto user){
        String temp ;
        if (user != null){
            User newU = modelMapper.map(user, User.class);
            //Hash zum speichern des PW in der Datenbank
            temp = getSHA256(newU.getPassword());
            newU.setPassword(temp);
            try{
                userRepository.save(newU);
            }catch (Exception e){   //TODO: nur eigentliche Exception behandeln: java.sql.SQLIntegrityConstraintViolationException
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already used");
            }

            //JWT Token generieren
            return JwtUtil.generateToken(new Auth(user.getEmail(), user.getPassword())) + "\n" + temp;
        }
        return "registration failed";
        //return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Integer id) {
        User existingUser = getUserFromRepoOrThrow(id);

        User user = modelMapper.map(userDto, User.class);
        user.setId(existingUser.getId());

        if (user.getPassword() == null) {
            // no password provided -> use existing password
            user.setPassword(existingUser.getPassword());
        }else{
            user.setPassword(getSHA256(user.getPassword()));
        }
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }


    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {
        final UserDto existingUserDto = getUser(id);
        userRepository.deleteById(id);
        return existingUserDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }

    private User getUserFromRepoOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
