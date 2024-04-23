package com.example.junit_test.modules.products.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.products.controllers.ProductController;
import com.example.junit_test.modules.products.dto.ProductDto;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void testListProducts() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity());

        when(productService.list()).thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Success", productList), HttpStatus.OK));

        ResponseEntity<SystemResponse<List<ProductEntity>>> responseEntity = productController.list();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productList, responseEntity.getBody().getData());
    }

    @Test
    public void testGetProductById() {
        int productId = 1;
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setName("Test Product");

        when(productService.getById(productId)).thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Success", product), HttpStatus.OK));

        ResponseEntity<SystemResponse<ProductEntity>> responseEntity = productController.getById(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody().getData());
    }

    @Test
    public void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");

        when(productService.create(productDto)).thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.CREATED.value(), "Created", true), HttpStatus.CREATED));

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.create(productDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().getData());
    }

    @Test
    public void testUpdateProduct() {
        int productId = 1;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");

        when(productService.update(productId, productDto)).thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Updated", true), HttpStatus.OK));

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.update(productId, productDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().getData());
    }

    @Test
    public void testDeleteProduct() {
        int productId = 1;

        when(productService.delete(productId)).thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Deleted", true), HttpStatus.OK));

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.delete(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().getData());
    }
}
