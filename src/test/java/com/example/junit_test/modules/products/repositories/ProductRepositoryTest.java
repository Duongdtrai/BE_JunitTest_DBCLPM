package com.example.junit_test.modules.products.repositories;

import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  public void testFindAllByIsDeletedFalse() {
    List<Product> productList = new ArrayList<>();
    productList.add(new Product());
    Page<Product> productPage = new PageImpl<>(productList);

    when(productRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(productPage);


    Page<Product> result = (Page<Product>) productService.list(0, 10);


    assertEquals(productList.size(), result.getContent().size());
  }


  @Test
  public void testDeleteById() {
    // Mocking the behavior of the repository method
    int productId = 1;
    when(productRepository.existsById(productId)).thenReturn(true);

    // Calling the service method that uses the repository
    productService.delete(productId);

    // Verifying the interaction
    verify(productRepository, times(1)).existsById(productId);
    verify(productRepository, times(1)).deleteById(productId);
  }
}