package com.example.junit_test.modules.products.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.repositories.CategoryRepository;
import com.example.junit_test.modules.products.dto.ProductDto;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<SystemResponse<List<ProductEntity>>> list() {
        try {
            List<ProductEntity> data = productRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<ProductEntity>> getById(Integer id) {
        try {
            ProductEntity data = productRepository.findProductEntitiesById(id);
            if (data == null) {
                return Response.badRequest(404, "Product is not exist");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> create(ProductDto product) {
        try {
            ProductEntity productNew = new ProductEntity();

            CategoryEntity categoryExist = categoryRepository.findByIdAndIsDeletedFalse(product.getCategory().getId());
            if (categoryExist == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            productNew.setImage(product.getImage());
            productNew.setName(product.getName());
            productNew.setQuantity(product.getQuantity());
            productNew.setPrice(product.getPrice());
            productNew.setDescription(product.getDescription());
            productNew.setCategory(product.getCategory());
            productRepository.save(productNew);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, ProductDto product) {
        try {
            ProductEntity productExist = productRepository.findProductEntitiesById(id);
            if (productExist == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            CategoryEntity categoryExist = categoryRepository.findByIdAndIsDeletedFalse(product.getCategory().getId());
            if (categoryExist == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            productExist.setImage(product.getImage());
            productExist.setName(product.getName());
            productExist.setQuantity(product.getQuantity());
            productExist.setPrice(product.getPrice());
            productExist.setDescription(product.getDescription());
            productExist.setCategory(product.getCategory());
            productRepository.save(productExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
        try {
            ProductEntity productExist = productRepository.findProductEntitiesById(id);
            if (productExist == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            productRepository.delete(productExist);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
