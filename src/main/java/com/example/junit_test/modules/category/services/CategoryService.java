package com.example.junit_test.modules.category.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.dto.CategoryDto;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;


    public ResponseEntity<SystemResponse<ResponsePage<CategoryEntity>>> list(int page, int size) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
            Pageable paging = PageRequest.of(page, size, sort);
            Page<CategoryEntity> data = categoryRepository.findAllByIsDeletedFalse(paging);
            return Response.ok(new ResponsePage<CategoryEntity>(data));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<CategoryEntity>> getById(Integer id) {
        try {
            CategoryEntity data = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (data == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<CategoryEntity>> create(CategoryDto category) {
        try {
            CategoryEntity categoryExist = categoryRepository.findByNameAndIsDeletedFalse(category.getName());
            if (categoryExist == null) {
                CategoryEntity newCategory = new CategoryEntity();
                newCategory.setName(category.getName());
                newCategory.setIsDeleted(false);
                newCategory = categoryRepository.save(newCategory);
                return Response.ok(newCategory);
            }
            return Response.badRequest(404, "Danh mục đã trùng name");

        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, CategoryDto category) {
        try {
            CategoryEntity categoryExist = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (categoryExist == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            categoryExist.setName(category.getName());
            categoryRepository.save(categoryExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }

    }

    public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
        try {
            CategoryEntity categoryExist = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (categoryExist == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            categoryExist.setIsDeleted(true);
            categoryRepository.save(categoryExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
