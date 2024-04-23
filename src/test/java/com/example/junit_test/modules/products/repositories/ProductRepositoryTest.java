package com.example.junit_test.modules.products.repositories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {

        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("Test Product");
        product.setIsDeleted(false);


        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(1))
                .thenReturn(product);

        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(2))
                .thenReturn(null);
    }

    @Test
    public void testFindProductEntitiesByIdAndIsDeletedFalse() {

        ProductEntity foundProduct = productRepository.findProductEntitiesByIdAndIsDeletedFalse(1);
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
        assertFalse(foundProduct.getIsDeleted());

        ProductEntity notFoundProduct = productRepository.findProductEntitiesByIdAndIsDeletedFalse(2);
        assertNull(notFoundProduct);
    }
}