package com.example.demo.services;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product=new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepo.save(product);
        return maToProductResponse(savedProduct);

    }

    private ProductResponse maToProductResponse(Product savedProduct) {
        ProductResponse pro=new ProductResponse();
        pro.setCategory(savedProduct.getCategory());
        pro.setDescription(savedProduct.getDescription());
        pro.setName(savedProduct.getName());
        pro.setPrice(savedProduct.getPrice());
        pro.setStockQuantity(savedProduct.getStockQuantity());
        pro.setImageUrl(savedProduct.getImageUrl());
        return pro;
    }

    public void updateProductFromRequest(Product product,ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setActive(productRequest.getActive() );

    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findByActiveTrue().stream()
                .map(this::maToProductResponse)
                .collect(Collectors.toList());

    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepo.searchProducts(keyword).stream()
                .map(this::maToProductResponse)
                .collect(Collectors.toList());

    }

    public boolean deleteProduct(Long id) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return true;
                }).orElse(false);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest req) {
        return productRepo.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, req);
                    Product savedProduct = productRepo.save(existingProduct);
                    return maToProductResponse(savedProduct);
                });
    }
}
