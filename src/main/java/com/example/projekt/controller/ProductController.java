package com.example.projekt.controller;

import com.example.projekt.dto.ProductDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService){
        this.productService = productService;
    }

    /*@GetMapping(produces = "raw/json")
    public @ResponseBody Product index(){
        //TODO
        return "String";
    }*/

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Integer id){
        return productService.getProduct(id);
    }

    @GetMapping("/all/")
    public List<ProductDto> getProducts(){
        return  getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable Integer id) {
        return productService.updateProduct(productDto, id);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable("id") Integer id){
        return productService.deleteProduct(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }
}
