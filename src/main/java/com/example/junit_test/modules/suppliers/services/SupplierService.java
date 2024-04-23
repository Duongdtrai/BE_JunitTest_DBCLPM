package com.example.junit_test.modules.suppliers.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SupplierService {
  private final SupplierRepository supplierRepository;

  public ResponseEntity<SystemResponse<ResponsePage<Supplier>>> list(int page, int size) {
    try {
      Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
      Pageable paging = PageRequest.of(page, size, sort);
      Page<Supplier> data = supplierRepository.findAllByIsDeletedFalse(paging);
      return Response.ok(new ResponsePage<Supplier>(data));
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Supplier>> getById(Integer id) {
    try {
      Supplier data = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
      if (data == null) {
        return Response.badRequest(404, "Danh mục không tồn tại");
      }
      return Response.ok(data);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Boolean>> create(Supplier supplier) {
    try {
      Supplier supplierExist = supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber());
      if (supplierExist == null) {
        supplierRepository.save(supplier);
        return Response.ok(true);
      }
      return Response.badRequest(404, "Supplier is exist name");
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Boolean>> update(Integer id, Supplier supplier) {
    try {
      Supplier existingSupplier = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
      if (existingSupplier == null) {
        return Response.badRequest(404, "Nhà cung cấp không tồn tại");
      }

      Supplier supplierExist = supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber());
      if (supplierExist != null) {
        return Response.badRequest(404, "Supplier is exist name");
      }
      supplier.setId(id);
      supplierRepository.save(supplier);
      return Response.ok(true);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
    try {
      Supplier supplier = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
      if (supplier == null) {
        return Response.badRequest(404, "Nhà cung cấp không tồn tại");
      }
      supplier.setIsDeleted(true);
      supplierRepository.save(supplier);
      return Response.ok(true);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Boolean>> deleteAll(Integer[] ids) {
    try {
      int count = supplierRepository.countAllByIdIn(Arrays.asList(ids));
      if (count != ids.length) {
        return Response.badRequest(404, "Nhà cung cấp không tồn tại");
      }
      supplierRepository.deleteAllById(Arrays.asList(ids));
      return Response.ok(true);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }
}
