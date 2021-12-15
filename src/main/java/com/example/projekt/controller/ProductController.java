package com.example.projekt.controller;

import com.example.projekt.dto.ProductDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.model.Product;
import com.example.projekt.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ModelMapper modelMapper = new ModelMapper();
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    /*@GetMapping(produces = "raw/json")
    public @ResponseBody Product index(){
        //TODO
        return "String";
    }*/

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Integer id){
        return modelMapper.map(getProductFromRepoOrThrow(id), ProductDto.class);
    }

    @GetMapping("/all/")
    public List<ProductDto> getProducts(){
        return modelMapper.map(productRepository.findAll(),
                new TypeToken<List<ProductDto>>(){}.getType());
        /*
        try{return (List<Product>) productRepository.findAll();}
        catch (Exception e){
            return null;
        }*/

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        try {
            return modelMapper.map(productRepository.save(product), ProductDto.class);
        } catch (Exception ex) {
            // unique constraint violation
            // TODO: there might be a better way to catch this
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name already used");
        }
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable Integer id) {
        Product existingProduct = getProductFromRepoOrThrow(id);

        Product product = modelMapper.map(productDto, Product.class);
        product.setId(existingProduct.getId());

        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable("id") Integer id){
        ProductDto existingProduct = getProduct(id);
        productRepository.deleteById(id);
        return existingProduct;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }

    private Product getProductFromRepoOrThrow(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
