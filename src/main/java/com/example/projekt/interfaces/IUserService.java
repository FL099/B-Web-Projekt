package com.example.projekt.interfaces;

import com.example.projekt.dto.UserDto;
import com.example.projekt.data.model.User;

public interface IUserService {
    boolean checkEmailExists(String email);
    Iterable<UserDto> getUsers();
    UserDto getSingleUser(int id);
    User getUserFromRepoOrThrow(Integer id);
    String createUser(UserDto user);
    UserDto updateUser(UserDto userDto, int id);
    UserDto deleteUser(int id);
}
