package com.example.projekt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long Id;

    @NotBlank
    private String name;

    private String productpic;

    private String description;

    @Positive(message = "contents muss >0 sein (in Liter)")
    private Integer contents;

    private String category;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductpic() {
        return productpic;
    }

    public void setProductpic(String productpic) {
        this.productpic = productpic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
