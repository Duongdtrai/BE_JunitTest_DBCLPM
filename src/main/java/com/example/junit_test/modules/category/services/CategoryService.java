package com.example.junit_test.modules.category.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;


    public ResponseEntity<SystemResponse<ResponsePage<Category>>> list(int page, int size) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
            Pageable paging = PageRequest.of(page, size, sort);
            Page<Category> data = categoryRepository.findAllByIsDeletedFalse(paging);
            return Response.ok(new ResponsePage<Category>(data));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Category>> getById(Integer id) {
        try {
            Category data = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (data == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Category>> create(Category category) {
        try {
            Category categoryExist = categoryRepository.findByNameAndIsDeletedFalse(category.getName());
            if (categoryExist == null) {
                return Response.ok(categoryRepository.save(category));
            }
            return Response.badRequest(404, "Danh mục đã trùng name");
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, Category category) {
        try {
            Category categoryExist = categoryRepository.findByIdAndIsDeletedFalse(id);
            if (categoryExist == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            categoryRepository.save(category);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
        try {
            Category categoryExist = categoryRepository.findByIdAndIsDeletedFalse(id);
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

    public ResponseEntity<SystemResponse<Boolean>> deleteAll(Integer[] ids) {
        try {
            int count = categoryRepository.countAllByIdIn(Arrays.asList(ids));
            if (count != ids.length) {
                return Response.badRequest(404, "Danh muc không tồn tại");
            }
            categoryRepository.deleteAllById(Arrays.asList(ids));
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
