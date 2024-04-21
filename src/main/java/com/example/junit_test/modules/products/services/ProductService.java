package com.example.junit_test.modules.products.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.repositories.ProductRepository;
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
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ResponseEntity<SystemResponse<ResponsePage<Product>>> list(int page, int size) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
            Pageable paging = PageRequest.of(page, size, sort);
            Page<Product> data = productRepository.findAllByIsDeletedFalse(paging);
            return Response.ok(new ResponsePage<Product>(data));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Product>> getById(Integer id) {
        try {
            Product data = productRepository.findProductEntitiesByIdAndIsDeletedFalse(id);

            if (data == null) {
                return Response.badRequest(404, "Product is not exist");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> create(Product product) {
        try {
            Category categoryExist = categoryRepository.findByIdAndIsDeletedFalse(product.getCategoryId());
            if (categoryExist == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }
            productRepository.save(product);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, Product product) {
        try {
            Product productExist = productRepository.findProductEntitiesByIdAndIsDeletedFalse(id);
            Category categoryExist = categoryRepository.findByIdAndIsDeletedFalse(product.getCategoryId());
            if (productExist == null) {
                return Response.badRequest(404, "Sản phẩm không tồn tại");
            }
            if (categoryExist == null) {
                return Response.badRequest(404, "Danh mục không tồn tại");
            }

            product.setId(id);
            productRepository.save(product);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
        try {
            Product productExist = productRepository.findProductEntitiesByIdAndIsDeletedFalse(id);
            if (productExist == null) {
                return Response.badRequest(404, "Sản phẩm không tồn tại");
            }
            productExist.setIsDeleted(true);
            productRepository.save(productExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> deleteAll(Integer[] ids) {
        try {
            int count = productRepository.countAllByIdIn(Arrays.asList(ids));
            if (count != ids.length) {
                return Response.badRequest(404, "Sản phẩm không tồn tại");
            }
            productRepository.deleteAllById(Arrays.asList(ids));
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
