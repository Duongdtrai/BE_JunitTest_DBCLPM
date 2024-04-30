package com.example.junit_test.modules.orders.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.entities.Supplier;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.webjars.NotFoundException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
  @Autowired
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final SupplierRepository supplierRepository;

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

  public ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> list(int page, int size, Boolean status, String keySearch) {
    try {
      Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
      Pageable paging = PageRequest.of(page, size, sort);
      Page<ImportOrder> order = status == null ? orderRepository.findAll(paging) : orderRepository.findAllByStatus(paging, status);
      return Response.ok(new ResponsePage<ImportOrder>(order));
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }




  //  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<SystemResponse<Boolean>> create(@RequestBody ImportOrder importOrder) {
    try {
      List<ImportOrder> importOrderExist = orderRepository.findImportOrderByCode(importOrder.getCode());
      if (!importOrderExist.isEmpty()) {
        return Response.badRequest(400, "Code đã tồn tại trong hệ thống");
      }
      Supplier supplierExist = this.supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId());
      if (supplierExist == null) {
        return Response.badRequest(404, "Nhà cung cấp không tồn tại");
      }
      Double payment = (double) 0;
      if (importOrder.getImportOrderProducts().isEmpty()) {
        return Response.badRequest(400, "Đơn hàng cần phải có sản phẩm");
      }
      for (ImportOrderProduct value : importOrder.getImportOrderProducts()) {
        payment += value.getImportPrice() * value.getQuantity();
        value.setImportOrder(importOrder);
        Product productExist = this.productRepository.findProductEntitiesByIdAndIsDeletedFalse(value.getProductId());
        if (productExist == null) {
          return Response.badRequest(404, "Sản phẩm không tồn tại");
        }
        if (productExist.getPrice() < value.getImportPrice()) {
          return Response.badRequest(400, "Giá nhập không được lớn hơn giá bán");
        }
      }
      payment += importOrder.getTax();
      importOrder.setPayment(payment);
      orderRepository.save(importOrder);
      return Response.ok(true);
    } catch (Exception e) {
//      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return Response.badRequest(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }


  //  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<SystemResponse<Boolean>> update(Integer id, @RequestBody ImportOrder importOrder) {
    try {
      ImportOrder importOrderExist = orderRepository.findImportOrderByIdAndStatusIsFalse(id);
      if (importOrderExist == null) {
        return Response.badRequest(404, "Đơn hàng không được sửa đổi");
      }
      if (!importOrderExist.getCode().equalsIgnoreCase(importOrder.getCode())) {
        return Response.badRequest(400, "Đơn hàng không được thay đổi mã sản phẩm");
      }
      if (importOrder.getImportOrderProducts().isEmpty()) {
        return Response.badRequest(400, "Đơn hàng cần phải có sản phẩm");
      }
      Supplier supplierExist = this.supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId());
      if (supplierExist == null) {
        return Response.badRequest(404, "Nhà cung cấp không tồn tại");
      }
      Double payment = (double) 0;
      for (ImportOrderProduct value : importOrder.getImportOrderProducts()) {
        payment += value.getImportPrice() * value.getQuantity();
        value.setImportOrder(importOrder);
        Product productExist = this.productRepository.findProductEntitiesByIdAndIsDeletedFalse(value.getProductId());
        if (productExist == null) {
          return Response.badRequest(404, "Sản phẩm không tồn tại");
        }
        if (productExist.getPrice() < value.getImportPrice()) {
          return Response.badRequest(400, "Giá nhập không được lớn hơn giá bán");
        }
      }
      payment += importOrder.getTax();
      importOrder.setPayment(payment);
      importOrder.setId(id);
      orderRepository.save(importOrder);
      return Response.ok(true);
    } catch (Exception e) {
//      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
      orderRepository.deleteById(orderId);
      return Response.ok(true);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

//  public void importFile(MultipartFile file) {
//    try {
//      List<Object> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
//      System.out.println("tutorials: " + tutorials);
//    } catch (IOException e) {
//      throw new RuntimeException("fail to store excel data: " + e.getMessage());
//    }
//  }
}
