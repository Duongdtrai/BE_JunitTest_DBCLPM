package com.example.junit_test.modules.orders.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.ExcelHelper;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import com.example.junit_test.modules.orders.repositories.OrderProductRepository;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> list(int page, int size) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
            Pageable paging = PageRequest.of(page, size, sort);
            Page<ImportOrder> order = orderRepository.findAll(paging);
            return Response.ok(new ResponsePage<ImportOrder>(order));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }


    public ResponseEntity<SystemResponse<ImportOrder>> getById(Integer orderId) {
        try {
            ImportOrder data = orderRepository.findOrderEntitiesById(orderId);
            if (data == null) {
                return Response.badRequest(404, "Đơn đặt hàng không tồn tại");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SystemResponse<ImportOrder>> create(@RequestBody ImportOrder importOrder) {
        try {
            for (ImportOrderProduct value : importOrder.getImportOrderProducts()) {
                value.setImportOrder(importOrder);
            }
            return Response.ok(orderRepository.save(importOrder));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.badRequest(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    public ResponseEntity<SystemResponse<Boolean>> updateStatus(Integer orderId) {
        try {
            ImportOrder existingOrder = orderRepository.findOrderEntitiesById(orderId);
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


    @Transactional
    public ResponseEntity<SystemResponse<Boolean>> delete(Integer orderId) {
        try {
            ImportOrder existingOrder = orderRepository.findOrderEntitiesById(orderId);
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
