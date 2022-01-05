package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.interfaces.IUserService;
import com.example.projekt.model.Auth;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.services.UserService;
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

    private IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/mail/{mail}")     //TODO: bei account Erstellung zum prüfen ob mail verfügbar, beim anmelden, ob nicht verfügbar(->heißt der Account existiert)
    public @ResponseBody Boolean isEmailAvailable(@PathVariable("mail") String email){
        return userService.checkEmailExists(email);
    }

    @GetMapping     //TODO: löschen, wenns nicht mehr zum testen gebraucht wird
    public Iterable<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")    //TODO: löschen, wenns nicht mehr zum testen gebraucht wird
    public UserDto getUser(@PathVariable Integer id) {
        return userService.getSingleUser(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody @Valid UserDto user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Integer id) {
        return userService.updateUser(userDto, id);
    }


    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }
}
