package com.example.projekt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotBlank(message = "name darf nicht leer sein")
    private String name;

    @Positive(message = "contents muss >0 sein (in Liter)")
    private Integer contents;

    public Product(String name) {
        this(name, null);
    }

    public Product(String name, Integer contents) {
        this.name = name;
    }

    public Product(){}
}
