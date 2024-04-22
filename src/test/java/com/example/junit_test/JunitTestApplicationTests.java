package com.example.junit_test;

import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class JunitTestApplicationTests {

  @Test
  void contextLoads() {
  }

  @BeforeAll
  public static void initData(
          @Autowired ProductRepository productRepository,
          @Autowired SupplierRepository supplierRepository,
          @Autowired CategoryRepository categoryRepository,
          @Autowired OrderRepository orderRepository) {
    System.out.println("========INIT DATA========");
    categoryRepository.saveAll(MockData.getMockDataCategories());
    supplierRepository.saveAll(MockData.getMockDataSuppliers());
    productRepository.saveAll(MockData.getMockDataProducts());
    orderRepository.save(MockData.getMockDataImportOrder());
    System.out.println("========INIT DATA DONE========");
  }
}
