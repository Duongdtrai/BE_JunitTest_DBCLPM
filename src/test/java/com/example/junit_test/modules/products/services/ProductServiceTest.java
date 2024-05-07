package com.example.junit_test.modules.products.services;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.category.services.CategoryService;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductService productService;

  @InjectMocks
  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Get Existing Product By Id")
  public void testFindById() {
    Product product = Product.builder().id(1).name("Test product").isDeleted(false).price(1000).categoryId(1).quantity(100).build();

    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(product);

    ResponseEntity<SystemResponse<Product>> response = productService.getById(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(Objects.requireNonNull(response.getBody()));
    assertNotNull(response.getBody().getData());
    assertEquals(product, response.getBody().getData());
  }

  @Test
  @DisplayName("Get Non Existing Product By Id")
  public void getNonExistingSupplierById() {
    Integer nonExistingProductId = 2;
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(nonExistingProductId)).thenReturn(null);
    ResponseEntity<SystemResponse<Product>> response = productService.getById(nonExistingProductId);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    assertEquals("Product is not exist", response.getBody().getMessage());
  }

  @Test
  @DisplayName("List Product")
  void testListProducts() {
    // Tạo dữ liệu mẫu cho trang đầu tiên
    List<Product> listProductMock = new ArrayList<>();
    Product product1 = Product.builder().name("Product 1").categoryId(1).quantity(100).build();
    Product product2 = Product.builder().name("Product 2").categoryId(2).quantity(200).build();
    listProductMock.add(product1);
    listProductMock.add(product2);
    Page<Product> samplePage = new PageImpl<>(listProductMock);

    // Thiết lập điều kiện giả cho phương thức findAllByIsDeletedFalse của repository trả về dữ liệu mẫu
    when(productRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(samplePage);

    // Gọi phương thức cần kiểm tra
    ResponseEntity<SystemResponse<ResponsePage<Product>>> response = productService.list(0, 10);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    Object data = response.getBody().getData().getData();
    int size;
    if (data instanceof List) {
      size = ((List<?>) data).size();
    } else if (data instanceof String) {
      size = ((String) data).length();
    } else {
      size = -1;
    }
    assertEquals(listProductMock.size(), size);
  }

  @Test
  @DisplayName("Create success Category")
  public void createSuccessNewCategory() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(null);

    ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(response.getBody().getData(), true);;;
  }

  @Test
  @DisplayName("Create fail Category without Name")
  public void createFailNewCategoryWithoutName() {
    Category category = Category.builder().id(1).isDeleted(false).build();
    when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(null);

    ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Category is exist name", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);;;
  }

  @Test
  @DisplayName("Delete Category")
  public void deleteCategory() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    ResponseEntity<SystemResponse<Boolean>> response = categoryService.delete(category.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());;;
  }

  @Test
  @DisplayName("Create fail Category with duplicated")
  public void createFaildNewCategoryWithDuplicated() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

    when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(category);

    ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Category is exist name", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);;;
  }

  @Test
  @DisplayName("List Category")
  public void getListCategory() {
    List<Category> categories = new ArrayList<>();
    Category category1 = Category.builder().name("Category 1").build();
    Category category2 = Category.builder().name("Category 2").build();
    categories.add(category1);
    categories.add(category2);
    Page<Category> samplePage = new PageImpl<>(categories);

    // Thiết lập điều kiện giả cho phương thức findAllByIsDeletedFalse của repository trả về dữ liệu mẫu
    when(categoryRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(samplePage);

    // Gọi phương thức cần kiểm tra
    ResponseEntity<SystemResponse<ResponsePage<Category>>> response = categoryService.list(0, 10);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    Object data = response.getBody().getData().getData();
    int size;
    if (data instanceof List) {
      size = ((List<?>) data).size();
    } else if (data instanceof String) {
      size = ((String) data).length();
    } else {
      size = -1;
    }
    assertEquals(categories.size(), size);
  }

  @Test
  @DisplayName("Test update success category")
  public void updateSuccessCategory() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Category updatedCategory = Category.builder().id(1).name("Test category 2").isDeleted(false).build();

    ResponseEntity<SystemResponse<Boolean>> response = categoryService.update(category.getId(), updatedCategory);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());;;
  }

  @Test
  @DisplayName("Test update fail category with duplicated")
  public void updateFailCategory() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Category duplicatedCategory = Category.builder().id(2).name("Test category 2").isDeleted(false).build();
    when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(duplicatedCategory);

    Category updatedCategory = Category.builder().id(1).name("Test category 2").isDeleted(false).build();
    ResponseEntity<SystemResponse<Boolean>> response = categoryService.update(category.getId(), updatedCategory);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(null, response.getBody().getData());
  }

  @Test
  @DisplayName("Create success product")
  public void creatSuccessProduct() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).name("Test product").isDeleted(false).price(1000).categoryId(1).quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(null);
    ResponseEntity<SystemResponse<Boolean>> response = productService.create(product);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  @DisplayName("Create fail product without category")
  public void creatFailProductWithoutCategory() {
    Product product = Product.builder().id(1).name("Test product").isDeleted(false).price(1000).quantity(100).build();

    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(null);
    ResponseEntity<SystemResponse<Boolean>> response = productService.create(product);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Danh mục không tồn tại", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Create fail product without name")
  public void creatFailProductWithoutName() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).isDeleted(false).price(1000).quantity(100).build();

    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(null);

    ResponseEntity<SystemResponse<Boolean>> response = productService.create(product);
    System.out.println(response);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Name must be not blank", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Create fail product without price")
  public void creatFailProductWithoutPrice() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).isDeleted(false).name("Test Product").quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(null);

    ResponseEntity<SystemResponse<Boolean>> response = productService.create(product);
    System.out.println(response);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Price must be not blank", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Update success Product")
  void testUpdateSuccessProduct() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).price(10000).isDeleted(false).name("Test Product").quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(product);

    Product updatedProduct = Product.builder().id(1).categoryId(1).price(200000).isDeleted(false).name("Test Product 2").quantity(100).build();

    ResponseEntity<SystemResponse<Boolean>> response = productService.update(1, updatedProduct);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  @DisplayName("Update fail Product with name")
  void testUpdateFailProductWithoutName() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).price(10000).isDeleted(false).name("Test Product").quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(product);

    Product updatedProduct = Product.builder().id(1).categoryId(1).price(200000).isDeleted(false).quantity(100).build();

    ResponseEntity<SystemResponse<Boolean>> response = productService.update(1, updatedProduct);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Name must be not blank", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Update fail Product with category")
  void testUpdateFailProductWithoutCategory() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).price(10000).isDeleted(false).name("Test Product").quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(product);

    Product updatedProduct = Product.builder().id(1).categoryId(null).price(200000).isDeleted(false).quantity(100).build();

    ResponseEntity<SystemResponse<Boolean>> response = productService.update(1, updatedProduct);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Danh mục không tồn tại", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Update fail Product with price")
  void testUpdateFailProductWithoutPrice() {
    Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
    when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

    Product product = Product.builder().id(1).categoryId(1).price(10000).isDeleted(false).name("Test Product").quantity(100).build();
    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(product.getId())).thenReturn(product);

    Product updatedProduct = Product.builder().id(1).categoryId(1).isDeleted(false).quantity(100).build();

    ResponseEntity<SystemResponse<Boolean>> response = productService.update(1, updatedProduct);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Price must be not blank", response.getBody().getMessage());
    assertEquals(response.getBody().getData(), null);
  }

  @Test
  @DisplayName("Delete product")
  void testDeleteProduct() {
    Product product = Product.builder().id(1).categoryId(1).price(10000).isDeleted(false).name("Test Product").quantity(100).build();

    when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt()))
            .thenReturn(product);

    ResponseEntity<SystemResponse<Boolean>> responseEntity = productService.delete(1);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}