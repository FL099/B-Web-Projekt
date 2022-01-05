package com.example.projekt.services;

import com.example.projekt.dto.ProductDto;
import com.example.projekt.interfaces.IProductService;
import com.example.projekt.data.model.Product;
import com.example.projekt.data.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ModelMapper modelMapper = new ModelMapper();
    private IProductRepository productRepository;

    public ProductService(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getProduct(int id) {
        return modelMapper.map(getProductFromRepoOrThrow(id), ProductDto.class);
    }

    @Override
    public List<ProductDto> getProducts() {
        return modelMapper.map(productRepository.findAll(),
                new TypeToken<List<ProductDto>>(){}.getType());
        /*
        try{return (List<Product>) productRepository.findAll();}
        catch (Exception e){
            return null;
        }*/
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        try {
            return modelMapper.map(productRepository.save(product), ProductDto.class);
        } catch (Exception ex) {
            // unique constraint violation
            // TODO: there might be a better way to catch this
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name already used");
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, int id) {
        Product existingProduct = getProductFromRepoOrThrow(id);

        Product product = modelMapper.map(productDto, Product.class);
        product.setId(existingProduct.getId());

        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(int id) {
        ProductDto existingProduct = getProduct(id);
        productRepository.deleteById(id);
        return existingProduct;
    }

    private Product getProductFromRepoOrThrow(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
