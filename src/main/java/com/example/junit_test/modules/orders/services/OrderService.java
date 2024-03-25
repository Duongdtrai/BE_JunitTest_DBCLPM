package com.example.junit_test.modules.orders.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.ExcelHelper;
import com.example.junit_test.modules.orders.dto.OrderDto;
import com.example.junit_test.modules.orders.dto.ProductOrderDto;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.entities.OrderProductEntity;
import com.example.junit_test.modules.orders.repositories.OrderProductRepository;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final SupplierRepository supplierRepository;

    public ResponseEntity<SystemResponse<ResponsePage<OrderEntity>>> list(int page, int size) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
            Pageable paging = PageRequest.of(page, size, sort);
            Page<OrderEntity> order = orderRepository.findAll(paging);
            return Response.ok(new ResponsePage<OrderEntity>(order));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }


    public ResponseEntity<SystemResponse<OrderEntity>> getById(Integer orderId) {
        try {
            OrderEntity data = orderRepository.findOrderEntitiesById(orderId);
            if (data == null) {
                return Response.badRequest(404, "Đơn đặt hàng không tồn tại");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SystemResponse<OrderEntity>> create(OrderDto order) {
        try {
            // check code order
            OrderEntity orderExist = orderRepository.findOrderByCode(order.getCode());
            if (orderExist != null) {
                throw new BadRequestException("Mã order bị trùng");
            }
            OrderEntity newOrder = new OrderEntity();
            SupplierEntity supplierExist = supplierRepository.findSupplierByIdAndIsDeletedFalse(order.getSupplier().getId());
            if (supplierExist == null) {
                throw new NotFoundException("Nhà cung cấp không tồn tại");
            }
            newOrder.setCode(order.getCode());
            newOrder.setNote(order.getNote());
            newOrder.setSupplier(supplierExist);
            newOrder.setStatus(false);
            newOrder.setTax(order.getTax());
            OrderEntity savedOrder = orderRepository.save(newOrder);
            List<OrderProductEntity> orderProducts = new ArrayList<>();
            for (ProductOrderDto productDto : order.getProducts()) {
                OrderProductEntity orderProduct = new OrderProductEntity();
                orderProduct.setOrder(savedOrder);
                ProductEntity product = productRepository.findProductEntitiesByIdAndIsDeletedFalse(productDto.getId());
                if (product != null) {
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(productDto.getQuantity());
                    orderProducts.add(orderProduct);
                } else {
                    throw new NotFoundException("Sản phẩm không tồn tại");
                }
            }
            orderProductRepository.saveAll(orderProducts);
            return Response.ok(savedOrder);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.badRequest(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    public ResponseEntity<SystemResponse<Boolean>> updateStatus(Integer orderId) {
        try {
            OrderEntity existingOrder = orderRepository.findOrderEntitiesById(orderId);
            if (existingOrder == null) {
                throw new NotFoundException("Đơn đặt hàng không tồn tại");
            }
            existingOrder.setStatus(true);
            orderRepository.save(existingOrder);
            return Response.ok(200, "Cập nhật trạng thái thanh toán tiền thành công");
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SystemResponse<Boolean>> update(Integer orderId, OrderDto order) {
        try {
            OrderEntity orderExist = orderRepository.findOrderByCode(order.getCode());
            if (orderExist != null && orderExist.getId() != orderId) {
                throw new BadRequestException("Mã order bị trùng");
            }
            OrderEntity existingOrder = orderRepository.findOrderEntitiesById(orderId);
            if (existingOrder == null) {
                throw new NotFoundException("Đơn đặt hàng không tồn tại");
            }
            if (existingOrder.getStatus()) {
                throw new BadRequestException("Đơn hàng đã được thanh toán, không được sửa đổi");
            }
            orderProductRepository.deleteByOrder_Id(existingOrder.getId());
            existingOrder.setStatus(existingOrder.getStatus());
            existingOrder.setCode(order.getCode());
            existingOrder.setNote(order.getNote());
            existingOrder.setTax(order.getTax());
            existingOrder.setOrderProducts(null);
            List<OrderProductEntity> orderProducts = new ArrayList<>();
            for (ProductOrderDto productDto : order.getProducts()) {
                OrderProductEntity orderProduct = new OrderProductEntity();
                orderProduct.setOrder(existingOrder);
                ProductEntity product = productRepository.findProductEntitiesByIdAndIsDeletedFalse(productDto.getId());
                if (product != null) {
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(productDto.getQuantity());
                    orderProducts.add(orderProduct);
                } else {
                    throw new NotFoundException("Sản phẩm không tồn tại");
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
                throw new NotFoundException("Đơn đặt hàng không tồn tại");
            }
            orderProductRepository.deleteByOrder_Id(orderId);
            orderRepository.deleteById(orderId);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public void importFile(MultipartFile file) {
        try {
            List<Object> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
            System.out.println("tutorials: " + tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
