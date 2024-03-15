package com.example.junit_test.services;

import com.example.junit_test.entities.ProductEntity;
import com.example.junit_test.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(Integer id) {
        return productRepository.findProductEntitiesById(id);
    };

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Integer id, ProductEntity product) {
        ProductEntity existingProduct = productRepository.findProductEntitiesById(id);

        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSupplier(product.getSupplier());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Integer id) {
        ProductEntity product = productRepository.findProductEntitiesById(id);
        productRepository.delete(product);
    }
}
