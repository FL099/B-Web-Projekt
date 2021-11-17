package com.example.projekt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 25)
    private String name;

    @Positive(message = "contents muss >0 sein (in Liter)")
    private Integer contents;

    private String category;

    public Product(String name) {
        this(name, null);
    }

    public Product(String name, Integer contents) {
        this(name, contents, null);
    }

    public Product(String name, Integer contents, String category) {
        this.name = name;
        this.contents = contents;
        this.category = category;
    }

    public Product(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContents() {
        return contents;
    }

    public void setContents(Integer contents) {
        this.contents = contents;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
