package com.example.junit_test.modules.category.services;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create success Category")
    public void createSuccessNewCategory() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(null);

        ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getData(), true);
        ;
        ;
    }

    @Test
    @DisplayName("Create Category with DB Error")
    public void createCategoryWithDBError() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByNameAndIsDeletedFalse(category.getName()))
                .thenThrow(new RuntimeException("Database error"));

        ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

        System.out.println(response);

        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());

    }

    @Test
    @DisplayName("Create fail Category without Name")
    public void createFailNewCategoryWithoutName() {
        Category category = Category.builder().id(1).isDeleted(false).build();
        when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(null);

        ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
        ;
        ;
    }

    @Test
    @DisplayName("Get success category by Id")
    public void getSuccessCategoryById() {
        Category category = Category.builder().id(1).isDeleted(false).build();
        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

        ResponseEntity<SystemResponse<Category>> response = categoryService.getById(category.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getData());
        ;
        ;
    }

    @Test
    @DisplayName("Get success category by Id with db error")
    public void getCategoryByIdWithDBErorr() {
        int catogoryId = 1;
        when(categoryRepository.findByIdAndIsDeletedFalse(catogoryId))
                .thenThrow(new RuntimeException("Database error"));
        ResponseEntity<SystemResponse<Category>> response = categoryService.getById(catogoryId);
        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Get fail category by Id")
    public void getFailCategoryById() {
        Category category = Category.builder().id(1).isDeleted(false).build();
        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(null);

        ResponseEntity<SystemResponse<Category>> response = categoryService.getById(category.getId());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
        ;
        ;
    }

    @Test
    @DisplayName("Delete Category")
    public void deleteCategory() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();
        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.delete(category.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getData());
        ;
        ;
    }

    @Test
    @DisplayName("Create fail Category with duplicated")
    public void createFaildNewCategoryWithDuplicated() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByNameAndIsDeletedFalse(category.getName())).thenReturn(category);

        ResponseEntity<SystemResponse<Category>> response = categoryService.create(category);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
        ;
        ;
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

        // Thiết lập điều kiện giả cho phương thức findAllByIsDeletedFalse của
        // repository trả về dữ liệu mẫu
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
    @DisplayName("List Category with DB error")
    public void getListCategoryWithDBError() {
        when(categoryRepository.findAllByIsDeletedFalse(any(Pageable.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Gọi phương thức cần kiểm tra
        ResponseEntity<SystemResponse<ResponsePage<Category>>> response = categoryService.list(0, 10);

        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Test update success category")
    public void updateSuccessCategory() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);

        Category updatedCategory = Category.builder().id(1).name("Test category 2").isDeleted(false).build();

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.update(category.getId(), updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getData());
        ;
        ;
    }

    @Test
    @DisplayName("Test update category with non exist category id")
    public void updateCategoryWithNonExistId() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId())).thenReturn(null);

        Category updatedCategory = Category.builder().id(1).name("Test category 2").isDeleted(false).build();

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.update(category.getId(), updatedCategory);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
        ;
        ;
    }

    @Test
    @DisplayName("Test update category with DB error")
    public void updateCategoryWithDBError() {
        Category category = Category.builder().id(1).name("Test category").isDeleted(false).build();

        when(categoryRepository.findByIdAndIsDeletedFalse(category.getId()))
                .thenThrow(new RuntimeException("Database error"));

        Category updatedCategory = Category.builder().id(1).name("Test category 2").isDeleted(false).build();

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.update(category.getId(), updatedCategory);

        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Test delete category with non exist category id")
    public void deleteCategoryWithNonExistId() {
        int categoryId = 1;

        when(categoryRepository.findByIdAndIsDeletedFalse(categoryId)).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.delete(categoryId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
        ;
        ;
    }

    @Test
    @DisplayName("Test delete category with db error")
    public void deleteCategoryWithDBError() {
        int categoryId = 1;

        when(categoryRepository.findByIdAndIsDeletedFalse(categoryId))
                .thenThrow(new RuntimeException("Database error"));

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.delete(categoryId);

        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Test success delete all")
    public void deleteSuccessAllCategory() {
        Category c1 = Category.builder().isDeleted(false).name("Category 1").id(1).build();
        Category c2 = Category.builder().isDeleted(false).name("Category 2").id(2).build();

        Integer[] categoryIdList = new Integer[2];
        categoryIdList[0] = c1.getId();
        categoryIdList[1] = c2.getId();

        when(categoryRepository.countAllByIdIn(Arrays.asList(categoryIdList))).thenReturn(2);

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.deleteAll(categoryIdList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getData());
    }

    @Test
    @DisplayName("Test delete all with ids not correct")
    public void deleteAllCategoryWithIdsListNotCorrect() {
        Integer[] categoryIdList = new Integer[0];

        when(categoryRepository.countAllByIdIn(Arrays.asList(categoryIdList))).thenReturn(1);

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.deleteAll(categoryIdList);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getData(), null);
    }

    @Test
    @DisplayName("Test delete all with db error")
    public void deleteAllCategoryWithDBError() {
        Integer[] categoryIdList = new Integer[0];

        when(categoryRepository.countAllByIdIn(Arrays.asList(categoryIdList)))
                .thenThrow(new RuntimeException("Database error"));

        ResponseEntity<SystemResponse<Boolean>> response = categoryService.deleteAll(categoryIdList);

        assertEquals("Database error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }
}