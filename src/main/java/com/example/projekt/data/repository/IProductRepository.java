package com.example.projekt.data.repository;

import com.example.projekt.data.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product, Integer> {
}
