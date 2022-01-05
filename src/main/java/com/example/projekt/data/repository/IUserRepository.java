package com.example.projekt.data.repository;

import com.example.projekt.data.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository extends CrudRepository<User, Integer> {
    List<User> findByEmailContaining(String email);
}
