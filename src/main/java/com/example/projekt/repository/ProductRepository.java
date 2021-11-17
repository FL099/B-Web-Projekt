package com.example.projekt.repository;

import com.example.projekt.model.Offer;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Offer, Integer> {
}
