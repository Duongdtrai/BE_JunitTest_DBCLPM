package com.example.junit_test.modules.suppliers.services;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SupplierServiceTest {
  @Mock
  private SupplierRepository supplierRepository;

  @InjectMocks
  private SupplierService supplierService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    reset(supplierRepository);
  }

  // =============== LIST ===============
  @Test
  @DisplayName("List Suppliers")
  public void listSuppliers() {
    // Tạo dữ liệu mẫu cho trang đầu tiên
    List<Supplier> listSupplierMock = new ArrayList<>();
    Supplier supplier1 = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    Supplier supplier2 = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    listSupplierMock.add(supplier1);
    listSupplierMock.add(supplier2);
    Page<Supplier> samplePage = new PageImpl<>(listSupplierMock);

    // Thiết lập điều kiện giả cho phương thức findAllByIsDeletedFalse của repository trả về dữ liệu mẫu
    when(supplierRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(samplePage);

    // Gọi phương thức cần kiểm tra
    ResponseEntity<SystemResponse<ResponsePage<Supplier>>> response = supplierService.list(0, 10);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    Object data = response.getBody().getData().getData();
    int size;
    if (data instanceof List) {
      size = ((List<?>) data).size();
    } else if (data instanceof String) {
      size = ((String) data).length();
    } else {
      size = -1;
    }
    assertEquals(listSupplierMock.size(), size);
  }

  // =============== GET BY ID ===============
  @Test
  @DisplayName("Get Existing Supplier By Id")
  public void GetExistingSupplierById() {
    // Tạo đối tượng Supplier mẫu
    int supplierId = 1;
    Supplier supplierExist = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(supplierExist);
    ResponseEntity<SystemResponse<Supplier>> response = supplierService.getById(supplierId);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    assertEquals(supplierExist, response.getBody().getData());
  }

  @Test
  @DisplayName("Get Non Existing Supplier By Id")
  public void getNonExistingSupplierById() {
    Integer nonExistingSupplierId = 2;
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(nonExistingSupplierId)).thenReturn(null);
    ResponseEntity<SystemResponse<Supplier>> response = supplierService.getById(nonExistingSupplierId);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    assertEquals("Supplier is not exist", response.getBody().getMessage());
  }


  @Test
  @DisplayName("Get Supplier By Id Database Error")
  public void getSupplierByIdDatabaseError() {
    int supplier = 2;
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplier)).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<SystemResponse<Supplier>> response = supplierService.getById(supplier);
    assertEquals("Database error", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }

  // =============== CREATE ===============

  @Test
  @DisplayName("Create supplier new supplier")
  public void createNewSupplier() {
    Supplier supplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();

    when(supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber())).thenReturn(null);
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.create(supplier);

    // Kiểm tra kết quả trả về
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  @DisplayName("Create supplier existing supplier")
  public void createExistingSupplier() {
    Supplier supplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber())).thenReturn(supplier);

    ResponseEntity<SystemResponse<Boolean>> response = supplierService.create(supplier);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Supplier is exist name", response.getBody().getMessage());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
  }


  @Test
  @DisplayName("Create Supplier With Exception")
  public void createSupplierWithException() {
    Supplier supplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();

    when(supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber())).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.create(supplier);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Database error", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }


  // =============== UPDATE ===============

  @Test
  @DisplayName("Update Existing Supplier")
  public void updateExistingSupplier() {
    int supplierId = 1;
    Supplier supplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(new Supplier());

    ResponseEntity<SystemResponse<Boolean>> response = supplierService.update(supplierId, supplier);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());

  }

  @Test
  @DisplayName("Update Existing Supplier With Same Info")
  public void updateExistingSupplierWithSameInfo() {
    int supplierId = 1;
    Supplier existingSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    Supplier updatedSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(existingSupplier);
    when(supplierRepository.findByNameAndAddressAndPhoneNumber(updatedSupplier.getName(), updatedSupplier.getAddress(), updatedSupplier.getPhoneNumber())).thenReturn(null);
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.update(supplierId, updatedSupplier);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  @DisplayName("Update Existing Supplier With Same Info Other")
  public void updateExistingSupplierWithSameInfoOther() {
    int supplierId = 1;
    Supplier existingSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    Supplier updatedSupplier = Supplier.builder().name("Duong1").address("Thanh Hoa2").phoneNumber("012399193").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(existingSupplier);
    when(supplierRepository.findByNameAndAddressAndPhoneNumber(updatedSupplier.getName(), updatedSupplier.getAddress(), updatedSupplier.getPhoneNumber())).thenReturn(updatedSupplier);
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.update(supplierId, updatedSupplier);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    assertEquals("Supplier is exist name and address and phone number", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Update Supplier With Database Error")
  public void updateSupplierWithDatabaseError() {
    int supplierId = 1;
    Supplier existingSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    Supplier updatedSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(existingSupplier);
    // Thiết lập điều kiện giả cho phương thức save để ném ra một Exception
    doThrow(new RuntimeException("Database error")).when(supplierRepository).save(updatedSupplier);

    ResponseEntity<SystemResponse<Boolean>> response = supplierService.update(supplierId, updatedSupplier);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Database error", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }


  // =============== DELETE ===============
  @Test
  @DisplayName("Delete Existing Supplier")
  public void deleteExistingSupplier() {
    int supplierId = 1;
    Supplier existingSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(existingSupplier);
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.delete(supplierId);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
    assertTrue(existingSupplier.getIsDeleted());
  }

  @Test
  @DisplayName("Delete Non Existing Supplier")
  public void deleteNonExistingSupplier() {
    int nonExistingSupplierId = 2;
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(nonExistingSupplierId)).thenReturn(null);
    ResponseEntity<SystemResponse<Boolean>> response = supplierService.delete(nonExistingSupplierId);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Supplier is not exist", response.getBody().getMessage());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
  }

  @Test
  @DisplayName("Delete Supplier With Database Error")
  public void deleteSupplierWithDatabaseError() {
    int supplierId = 1;
    Supplier existingSupplier = Supplier.builder().name("Duong").address("Thanh Hoa").phoneNumber("012399192").build();
    when(supplierRepository.findSupplierByIdAndIsDeletedFalse(supplierId)).thenReturn(existingSupplier);

    // Thiết lập điều kiện giả cho phương thức save để ném ra một Exception
    doThrow(new RuntimeException("Database error")).when(supplierRepository).save(existingSupplier);

    ResponseEntity<SystemResponse<Boolean>> response = supplierService.delete(supplierId);
    assertEquals("Database error", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }
}
