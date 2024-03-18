package com.example.junit_test.modules.orders.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.dto.OrderDto;
import com.example.junit_test.modules.orders.dto.ProductDto;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.entities.OrderProductEntity;
import com.example.junit_test.modules.orders.repositories.OrderProductRepository;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.webjars.NotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public ResponseEntity<SystemResponse<List<OrderEntity>>> list() {
        try {
            List<OrderEntity> order = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }


    public ResponseEntity<SystemResponse<OrderEntity>> getById(Integer orderId) {
        try {
            OrderEntity data = orderRepository.findOrderEntitiesById(orderId);
            if (data == null) {
                return Response.badRequest(404, "Order is not exist");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SystemResponse<OrderEntity>> create(OrderDto order) {
        try {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setSupplier(order.getSupplier());
            newOrder.setStatus(false);
            OrderEntity savedOrder = orderRepository.save(newOrder);
            List<OrderProductEntity> orderProducts = new ArrayList<>();
            for (ProductDto productDto : order.getProducts()) {
                OrderProductEntity orderProduct = new OrderProductEntity();
                orderProduct.setOrder(savedOrder);
                ProductEntity product = productRepository.findProductEntitiesByIdAndIsDeletedFalse(productDto.getId());
                if (product != null) {
                    if (product.getQuantity() < productDto.getQuantity()) {
                        throw new BadRequestException("Quantity is greater than quantity product");
                    }
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(productDto.getQuantity());
                    orderProduct.setTax(productDto.getTax());
                    orderProducts.add(orderProduct);
                } else {
                    throw new NotFoundException("Product is not exist");
                }
            }
            orderProductRepository.saveAll(orderProducts);
            return Response.ok(savedOrder);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SystemResponse<Boolean>> update(Integer orderId, OrderDto order) {
        try {
            OrderEntity existingOrder = orderRepository.findOrderEntitiesById(orderId);
            if (existingOrder == null) {
                throw new NotFoundException("Order is not exist");
            }
            orderProductRepository.deleteByOrder_Id(existingOrder.getId());
            existingOrder.setStatus(order.getStatus() != null ? order.getStatus() : existingOrder.getStatus());
            orderRepository.save(existingOrder);
            List<OrderProductEntity> orderProducts = new ArrayList<>();
            for (ProductDto productDto : order.getProducts()) {
                OrderProductEntity orderProduct = new OrderProductEntity();
                orderProduct.setOrder(existingOrder);
                ProductEntity product = productRepository.findProductEntitiesByIdAndIsDeletedFalse(productDto.getId());
                if (product != null) {
                    if (product.getQuantity() < productDto.getQuantity()) {
                        throw new BadRequestException("Quantity is greater than quantity product");
                    }
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(productDto.getQuantity());
                    orderProduct.setTax(productDto.getTax());
                    orderProducts.add(orderProduct);
                } else {
                    throw new NotFoundException("Product is not exist");
                }
            }
            orderProductRepository.saveAll(orderProducts);
            return Response.ok(true);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<SystemResponse<Boolean>> delete(Integer orderId) {
        try {
            OrderEntity existingOrder = orderRepository.findOrderEntitiesById(orderId);
            if (existingOrder == null) {
                throw new NotFoundException("Order is not exist");
            }
            orderProductRepository.deleteByOrder_Id(orderId);
            orderRepository.deleteById(orderId);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
