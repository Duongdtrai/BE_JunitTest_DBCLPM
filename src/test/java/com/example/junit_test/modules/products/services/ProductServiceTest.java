package com.example.junit_test.modules.products.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.products.dto.ProductDto;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.products.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    public void testList() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity());

        when(productRepository.findAll()).thenReturn(productList);

        ResponseEntity<SystemResponse<List<ProductEntity>>> responseEntity = productService.list();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productList, responseEntity.getBody().getData());
    }

    @Test
    public void testGetById() {
        int productId = 1;
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setName("Test Product");

        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(productId)).thenReturn(product);
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(2)).thenReturn(null);

        ResponseEntity<SystemResponse<ProductEntity>> responseEntity = productService.getById(productId);
        ResponseEntity<SystemResponse<ProductEntity>> responseEntityNotFound = productService.getById(2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody().getData());

        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNotFound.getStatusCode());
        assertNull(responseEntityNotFound.getBody().getData());
    }

    @Test
    public void testCreate() {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        productDto.setCategory(category);

        when(categoryRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(category);

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.create(productDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData());
    }

    @Test
    public void testUpdate() {
        int productId = 1;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        productDto.setCategory(category);

        ProductEntity productExist = new ProductEntity();
        productExist.setId(productId);

        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(productId)).thenReturn(productExist);
        when(categoryRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(category);

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.update(productId, productDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData());
    }

    @Test
    public void testDelete() {
        int productId = 1;
        ProductEntity productExist = new ProductEntity();
        productExist.setId(productId);

        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(productId)).thenReturn(productExist);

        ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.delete(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData());
    }
}
