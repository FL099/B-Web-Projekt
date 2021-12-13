package com.example.projekt.controller;

import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.model.Product;
import com.example.projekt.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        //TODO
        return new Product("product", 2, "Non-alk");
    }

    @GetMapping("/all/")
    public List<Product> returnProducts(){
        try{return (List<Product>) productRepository.findAll();}
        catch (Exception e){
            return null;
        }

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
        return Exceptionhandler.handleGeneralExceptions(ex);
    }
}
