package com.example.junit_test.modules.products.services;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testListProducts() {
    when(productRepository.findAllByIsDeletedFalse(any()))
            .thenReturn(Page.empty());

    ResponseEntity<SystemResponse<ResponsePage<Product>>> responseEntity = productService.list(0, 10);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testGetProductById() {
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(null);

    ResponseEntity<SystemResponse<Product>> responseEntity = productService.getById(1);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void testCreateProduct() {
    when(categoryRepository.findByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(new Category());

    ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.create(new Product());

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testUpdateProduct() {
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(new Product());
    when(categoryRepository.findByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(new Category());

    ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.update(1, new Product());

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testDeleteProduct() {
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(new Product());

    ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.delete(1);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testDeleteAllProducts() {
    when(productRepository.countAllByIdIn(anyList()))
            .thenReturn(3);
    when(productRepository.deleteAllById(anyList()))
            .thenReturn(null);

    ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.deleteAll(new Integer[]{1, 2, 3});

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}