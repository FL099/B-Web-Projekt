package com.example.projekt.interfaces;

import com.example.projekt.dto.ProductDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface IProductService {

    ProductDto getProduct(int id);
    List<ProductDto> getProducts();
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto, int id);
    ProductDto deleteProduct(int id);

}
