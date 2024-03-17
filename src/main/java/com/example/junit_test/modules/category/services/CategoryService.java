package com.example.junit_test.modules.category.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.dto.CategoryDto;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;


    public ResponseEntity<SystemResponse<List<CategoryEntity>>> list() {
        try {
            List<CategoryEntity> data = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<CategoryEntity>> getById(Integer id) {
        try {
            CategoryEntity data = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (data == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> create(CategoryDto category) {
        try {
            CategoryEntity categoryExist = categoryRepository.findByNameAndIsDeletedFalse(category.getName());
            if (categoryExist == null) {
                CategoryEntity newCategory = new CategoryEntity();
                newCategory.setName(category.getName());
                newCategory.setIsDeleted(false);
                categoryRepository.save(newCategory);
                return Response.ok(true);
            }
            return Response.badRequest(404, "Category is exist name");

        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, CategoryDto category) {
        try {
            CategoryEntity categoryExist = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (categoryExist == null) {
                return Response.badRequest(404, "Category is not exist");
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
                return Response.badRequest(404, "Category is not exist");
            }
            categoryExist.setIsDeleted(true);
            categoryRepository.save(categoryExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
