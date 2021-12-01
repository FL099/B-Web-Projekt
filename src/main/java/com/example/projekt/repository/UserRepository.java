package com.example.projekt.repository;

import com.example.projekt.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByEmailContaining(String email);
}
