package com.example.junit_test.modules.products.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ProductControllerTest {

  @Mock
  private ProductService productService;

  private ProductController productController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    productController = new ProductController(productService);
  }

  @Test
  void testListProducts() {
    when(productService.list(anyInt(), anyInt()))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Success", new ResponsePage<>()), HttpStatus.OK));
    ResponseEntity<SystemResponse<ResponsePage<Product>>> responseEntity = productController.list(0, 10);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testGetProductById() {
    when(productService.getById(100))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Success", new Product()), HttpStatus.OK));
    ResponseEntity<SystemResponse<Product>> responseEntity = productController.getById(100);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void testCreateProduct() {
    when(productService.create(any(Product.class)))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.CREATED.value(), "Created", true), HttpStatus.CREATED));
    ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.create(new Product(), null);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  void testUpdateProduct() {
    when(productService.update(anyInt(), any(Product.class)))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Updated", true), HttpStatus.OK));
    ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.update(1, new Product(), null);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testDeleteProduct() {
    when(productService.delete(anyInt()))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Deleted", true), HttpStatus.OK));
    ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.delete(1);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testDeleteProductsById() {
    when(productService.deleteAll(any(Integer[].class)))
            .thenReturn(new ResponseEntity<>(new SystemResponse<>(HttpStatus.OK.value(), "Deleted", true), HttpStatus.OK));
    ResponseEntity<SystemResponse<Boolean>> responseEntity = productController.deleteProductsById(new Integer[]{1, 2, 3});
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}