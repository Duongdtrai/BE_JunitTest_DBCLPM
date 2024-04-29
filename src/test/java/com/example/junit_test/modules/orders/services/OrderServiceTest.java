package com.example.junit_test.modules.orders.services;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.OrderMockData;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.repositories.ProductRepository;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private OrderService orderService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(orderRepository, productRepository, supplierRepository);
    }

//  ========================= Get list ===================
    @DisplayName("Get list of orders when status is null")
    @Test
    public void listOrdersReturnsOkResponseWhenStatusIsNull() {
        when(orderRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(OrderMockData.validRecords()));

        ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> response = orderService.list(0, 10, null, "");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Get list of orders when status is not null")
    @Test
    public void listOrdersReturnsOkResponseWhenStatusIsNotNull() {
        when(orderRepository.findAllByStatus(any(), anyBoolean())).thenReturn(new PageImpl<>(new ArrayList<>()));

        ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> response = orderService.list(0, 10, true, "");

        assertEquals(200, response.getStatusCodeValue());
        verify(orderRepository, times(1)).findAllByStatus(any(), anyBoolean());
    }


    @DisplayName("Get list of orders with exception")
    @Test
    public void listOrdersReturnsBadRequestWhenExceptionIsThrown() {
        when(orderRepository.findAll((Pageable) any())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> response = orderService.list(0, 10, null, "");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }


//  ========================== Get order by id =====================================

    @DisplayName("Get order by id returns bad request when order does not exist")
    @Test
    public void getByIdReturnsBadRequestWhenOrderDoesNotExist() {
        Integer orderId = 1;
        when(orderRepository.findOrderEntitiesById(orderId)).thenReturn(null);

        ResponseEntity<SystemResponse<ImportOrder>> response = orderService.getById(orderId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Đơn đặt hàng không tồn tại", response.getBody().getMessage());
    }

    @DisplayName("Get order by id returns bad request when exception occurs")
    @Test
    public void getByIdReturnsBadRequestWhenExceptionOccurs() {
        Integer orderId = 1;
        when(orderRepository.findOrderEntitiesById(orderId)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<SystemResponse<ImportOrder>> response = orderService.getById(orderId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }

    @DisplayName("Get order by id returns order when order exists")
    @Test
    public void getByIdReturnsOrderWhenOrderExists() {
        Integer orderId = 1;
        ImportOrder mockOrder = new ImportOrder();
        when(orderRepository.findOrderEntitiesById(orderId)).thenReturn(mockOrder);

        ResponseEntity<SystemResponse<ImportOrder>> response = orderService.getById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrder, response.getBody().getData());
    }
//  ========================== Create order =====================================

    @DisplayName("Create order with existing code")
    @Test
    public void createOrderWithExistingCode() {
        ImportOrder importOrder = new ImportOrder();
        importOrder.setCode("existingCode");

        List<ImportOrder> existingOrders = new ArrayList<>();
        existingOrders.add(new ImportOrder());

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(existingOrders);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Code đã tồn tại trong hệ thống", response.getBody().getMessage());
    }


    @DisplayName("Create order with non-existing supplier")
    @Test
    public void createOrderWithNonExistingSupplier() {
        ImportOrder importOrder = new ImportOrder();
        importOrder.setCode("newCode");
        importOrder.setSupplierId(1);

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nhà cung cấp không tồn tại", response.getBody().getMessage());
    }

    @DisplayName("Create order with empty products")
    @Test
    public void createOrderWithoutProducts() {
        ImportOrder importOrder = OrderMockData.invalidRecord_missingImportOrderProduct();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());

        System.out.println(importOrder.getImportOrderProducts().isEmpty());
        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Đơn hàng cần phải có sản phẩm", response.getBody().getMessage());
    }

    @DisplayName("Create order with non-existing product")
    @Test
    public void createOrderWithNonExistingProduct() {
        ImportOrder importOrder = OrderMockData.invalidRecord_nonExistingProduct();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sản phẩm không tồn tại", response.getBody().getMessage());
    }

    @DisplayName("Create order with import price greater than sale price")
    @Test
    public void createOrderWithImportPriceGreaterThanSalePrice() {
        ImportOrder importOrder = OrderMockData.validRecord();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(new Product());

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Giá nhập không được lớn hơn giá bán", response.getBody().getMessage());
    }

    @DisplayName("Create order with import price equal to 0")
    @Test
    public void createOrderWithImportPriceEqualToZero() {
        ImportOrder importOrder = OrderMockData.invalidRecord_importPriceEqualToZero();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(new Product());

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Giá nhập không được bằng 0", response.getBody().getMessage());
    }

    @DisplayName("Create order with quantity equal to 0")
    @Test
    public void createOrderWithQuantityEqualToZero() {
        ImportOrder importOrder = OrderMockData.invalidRecord_quantityEqualToZero();

        Product product = Product.builder().price(200L).build();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(product);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Số lượng sản phẩm không được bằng 0", response.getBody().getMessage());
    }



    @DisplayName("Create order successfully")
    @Test
    public void createOrderSuccessfully() {
        ImportOrder importOrder = OrderMockData.validRecord();

        Product product = new Product();
        product.setPrice(200.0);

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(product);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Create order with transaction rollback due to exception")
    @Transactional
    @Rollback
    @Test
    public void createOrderWithTransactionRollbackDueToException() {
        ImportOrder importOrder = OrderMockData.validRecord();

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }

    @DisplayName("Create order with transaction rollback due to product price exception")
    @Test
    public void createOrderWithTransactionRollbackDueToProductPriceException() {
        ImportOrder importOrder = OrderMockData.validRecord();

        Product product = new Product();
        product.setPrice(200.0);

        when(orderRepository.findImportOrderByCode(importOrder.getCode())).thenReturn(new ArrayList<>());
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(new Supplier());
        when(productRepository.findProductEntitiesByIdAndIsDeletedFalse(anyInt())).thenReturn(product);
        doThrow(new RuntimeException("Unexpected error")).when(orderRepository).save(any());

        ResponseEntity<SystemResponse<Boolean>> response = orderService.create(importOrder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }


//    ========================== Update order =====================================

    @DisplayName("Update order with non-existing order")
    @Test
    public void updateOrderWithNonExistingOrder() {
        ImportOrder importOrder = OrderMockData.validRecord();

        when(orderRepository.findOrderEntitiesById(1)).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.update(1, importOrder);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Đơn đặt hàng không tồn tại", response.getBody().getMessage());
    }

    @DisplayName("Update order is paid")
    @Test
    public void updateOrderIsPaid() {
        ImportOrder importOrder = OrderMockData.validRecord();
        importOrder.setStatus(false);
        importOrder.setId(100);

        when(orderRepository.findImportOrderByIdAndStatusIsFalse(100)).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.update(1, importOrder);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Đơn hàng không được sửa đổi", response.getBody().getMessage());
    }

    @DisplayName("Update order with different code")
    @Test
    public void updateOrderWithDifferentCode() {
        ImportOrder importOrder = OrderMockData.validRecord();
        importOrder.setId(1);
        ImportOrder importOrderExist = OrderMockData.validRecord();
        importOrderExist.setCode("TD633");

        when(orderRepository.findImportOrderByIdAndStatusIsFalse(1)).thenReturn(importOrderExist);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.update(1, importOrder);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Đơn hàng không được thay đổi mã đơn hàng", response.getBody().getMessage());
    }

    @DisplayName("Update order with empty products")
    @Test
    public void updateOrderWithEmptyProducts() {
        ImportOrder importOrder = OrderMockData.invalidRecord_missingImportOrderProduct();
        importOrder.setId(1);
        ImportOrder importOrderExist = OrderMockData.validRecord();

        when(orderRepository.findImportOrderByIdAndStatusIsFalse(1)).thenReturn(importOrderExist);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.update(1, importOrder);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Đơn hàng cần phải có sản phẩm", response.getBody().getMessage());
    }

    @DisplayName("Update order with non-existing supplier")
    @Test
    public void updateOrderWithNonExistingSupplier() {
        ImportOrder importOrder = OrderMockData.validRecord();
        importOrder.setId(1);
        ImportOrder importOrderExist = OrderMockData.validRecord();
        importOrderExist.setSupplierId(1);

        when(orderRepository.findImportOrderByIdAndStatusIsFalse(1)).thenReturn(importOrderExist);
        when(supplierRepository.findSupplierByIdAndIsDeletedFalse(importOrder.getSupplierId())).thenReturn(null);

        ResponseEntity<SystemResponse<Boolean>> response = orderService.update(1, importOrder);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nhà cung cấp không tồn tại", response.getBody().getMessage());
    }

}