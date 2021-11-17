package com.example.projekt.controller;

import com.example.projekt.model.Product;
import com.example.projekt.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping(produces = "raw/json")
    public @ResponseBody Product index(){
        return new Product("product", 2, "Non-alk");
        //return "{\"name\":\"test\",\"contents\": 1,\"category\":\"\"}";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid Product product){
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public Product deleteAuction(@PathVariable("id") Product product){
        productRepository.delete(product);
        return product;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
