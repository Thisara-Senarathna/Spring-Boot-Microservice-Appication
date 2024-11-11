package com.example.product.service;

import com.example.product.dto.ProductDTO;
import com.example.product.model.Product;
import com.example.product.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getProducts(){
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(productList,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public ProductDTO addProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO,Product.class));
        return productDTO;
    }

    public ProductDTO updateProduct(ProductDTO productDTO){
        Product product = productRepo.save(modelMapper.map(productDTO,Product.class));
        return productDTO;
    }

    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        return "product deleted";
    }

    public ProductDTO getProductById(Integer productId){
        Product product = productRepo.getProductById(productId);
        return modelMapper.map(product,ProductDTO.class);
    }

}
