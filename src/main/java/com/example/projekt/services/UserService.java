package com.example.projekt.services;

import com.example.projekt.dto.UserDto;
import com.example.projekt.interfaces.IUserService;
import com.example.projekt.model.Auth;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.projekt.util.crypt.getSHA256;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkEmailExists(String email){
        return !userRepository.findByEmailContaining(email).isEmpty();
        //TODO: falls besser: false wenns nicht verfügbar ist, true wenn schon
    }

    @Override
    public Iterable<UserDto> getUsers(){
        return modelMapper.map(userRepository.findAll(),
                new TypeToken<List<UserDto>>(){}.getType());
    }

    @Override
    public UserDto getSingleUser(int id){
        return modelMapper.map(getUserFromRepoOrThrow(id), UserDto.class);
    }

    @Override
    public String createUser(UserDto user){
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
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
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

    @Override
    public UserDto deleteUser(int id) {
        final UserDto existingUserDto = getSingleUser(id);
        userRepository.deleteById(id);
        return existingUserDto;
    }

    @Override
    public User getUserFromRepoOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
