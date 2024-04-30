package com.example.junit_test.modules.orders.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.OrderMockData;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.orders.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private OrderService orderService;

  @Mock
  private OrderRepository orderRepository;

  private ImportOrder importOrder;

  @InjectMocks
  private OrderController orderController;

//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this); // Initialize mocks
//    this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
//    importOrder = new ImportOrder();
//    importOrder.setId(1);
//    importOrder.setCode("Test Order");
//  }

  @Before
  public void setup() {
//    this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  //    ==================== GET LIST ====================
  @Test
  @DisplayName("Get list of orders with default parameters")
  void listWithDefaultParameters() throws Exception {
    ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> mockResponsePage = OrderMockData.validResponsePage();

    // When list method of orderService is called with any parameters, return the mock response
//    when(orderService.list(any(Integer.class), any(Integer.class), any(Boolean.class), any(String.class)))
//            .thenReturn(mockResponsePage);
    mockMvc.perform(get("/api/v1/orders"))
            .andExpect(status().isOk())
    ;
  }

  @Test
  @DisplayName("Get list of orders with custom parameters")
  void listWithCustomParameters() throws Exception {
//    SystemResponse<ResponsePage<ImportOrder>> systemResponse = new SystemResponse<>();
//    when(orderService.list(any(Integer.class), any(Integer.class), any(Boolean.class), any(String.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(get("/api/v1/orders?page=2&size=20&status=false&keySearch=test"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get list of orders with invalid status parameter")
  void listWithInvalidStatusParameter() throws Exception {
    mockMvc.perform(get("/api/v1/orders?status=invalid"))
            .andExpect(status().isBadRequest());
  }

  //    ==================== GET BY ID ====================
  @Test // loi: jakarta.servlet.ServletException: Handler dispatch failed: java.lang.StackOverflowError
  @DisplayName("Get an existing order by ID")
  void getExistingOrderById() throws Exception {
//    SystemResponse<ImportOrder> systemResponse = new SystemResponse<>();
//    systemResponse.setData(importOrder);
//    // Mock data cho cả hai trường hợp ID = 1 và ID = 45
//    when(orderService.getById(45)).thenReturn(ResponseEntity
//            .status(HttpStatus.OK)
//            .body(new SystemResponse<>(404, "123")));
    mockMvc.perform(get("/api/v1/orders/32"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get a non-existing order by ID")
  void getNonExistingOrderById() throws Exception {
    mockMvc.perform(get("/api/v1/orders/999"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              String message = jsonResponse.getString("message");
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
                assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              assertEquals("Đơn hàng không tồn tại", message);
            });
  }

  //    ==================== CREATE ====================
  @Test
  @DisplayName("Create a new order with valid data")
  @Transactional
  void createOrderWithValidData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(true);
//    when(orderService.create(any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.validRecord())))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Create a new order with invalid code")
  @Transactional
  void createOrderWithInvalidCode() throws Exception {
      mockMvc.perform(post("/api/v1/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(new ObjectMapper().writeValueAsString(OrderMockData.inValidRecord_inValidCode())))
              .andExpect(status().isBadRequest())
              .andExpect(result -> {
                  String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                  System.out.println("responseMessage: " + responseMessage);
                  JSONObject jsonResponse = new JSONObject(responseMessage);

                  // check status
                  Integer status = Integer.valueOf(jsonResponse.getString("status"));
                  assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                  // check message
                  String message = jsonResponse.getString("message");
                  assertEquals("BAD_REQUEST", message);

                  // check code missing
                  JSONObject dataObject = jsonResponse.getJSONObject("data");
                  String code = dataObject.getString("code");
                  if (code != null)
                      assertEquals("Mã đơn hàng gồm 2 ký tự chữ theo sau là 3 số", code);
              });
  }

  @Test
  @DisplayName("Create a new order with missing data")
  @Transactional
  void createOrderWithMissingData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.create(any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingData())))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);

              // check code missing
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String code = dataObject.getString("code");
              if (code != null)
                assertEquals("Đơn hàng cần có mã đơn hàng", code);

              String tax = dataObject.getString("tax");
              if (tax != null)
                assertEquals("Đơn hàng cần có thuế", tax);

              String supplierId = dataObject.getString("supplierId");
              if (supplierId != null)
                assertEquals("Đơn hàng cần có nhà cung cấp", supplierId);

              String employeeId = dataObject.getString("employeeId");
              if (employeeId != null)
                  assertEquals("Đơn hàng cần có nhân viên thực hiện", employeeId);
            });
  }

  @Test
  @DisplayName("Create a new order with negative data")
  @Transactional
  void createOrderWithNegativeData() throws Exception {
    mockMvc.perform(post("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_negativeData("TD123"))))
      .andExpect(status().isBadRequest())
      .andExpect(result -> {
          String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
          System.out.println("responseMessage: " + responseMessage);
          JSONObject jsonResponse = new JSONObject(responseMessage);

          // check status
          Integer status = Integer.valueOf(jsonResponse.getString("status"));
          assertEquals(HttpStatus.BAD_REQUEST.value(), status);
          // check message
          String message = jsonResponse.getString("message");
          assertEquals("BAD_REQUEST", message);

          // check data
          JSONObject dataObject = jsonResponse.getJSONObject("data");
          String tax = dataObject.getString("tax");
          if (tax != null)
            assertEquals("Thuế phải lớn hơn 0", tax);

          String employeeId = dataObject.getString("employeeId");
          if (employeeId != null)
            assertEquals("Mã nhân viên phải lớn hơn 0", employeeId);

          String supplierId = dataObject.getString("supplierId");
          if (supplierId != null)
            assertEquals("Mã nhà cung cấp phải lớn hơn 0", supplierId);
      });
  }

  @Test
  @DisplayName("Create a new order with non-existing supplier")
  @Transactional
  void createOrderWithNonExistSupplier() throws Exception {
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistSupplier("TD123"))))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("Nhà cung cấp không tồn tại", message);
            });
  }

  @Test
  @DisplayName("Create a new order with non-existing employee")
  @Transactional
  void createOrderWithNonExistEmployee() throws Exception {
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistEmployee("TD123"))))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("Nhân viên không tồn tại", message);
            });
  }

  @Test
  @DisplayName("Create a new order with empty list product")
  @Transactional
  void createOrderWithEmptyOrderProduct() throws Exception {
      mockMvc.perform(post("/api/v1/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingImportOrderProduct("TD123"))))
              .andExpect(status().isBadRequest())
              .andExpect(result -> {
                  String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                  System.out.println("responseMessage: " + responseMessage);
                  JSONObject jsonResponse = new JSONObject(responseMessage);

                  // check status
                  Integer status = Integer.valueOf(jsonResponse.getString("status"));
                  assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                  // check message
                  String message = jsonResponse.getString("message");
                  assertEquals("Đơn hàng cần phải có sản phẩm", message);
              });
  }

  @Test
  @DisplayName("Create a new order with product have import price greater than sell price")
  @Transactional
  void createOrderWithProductImportPriceGreaterThanSellPrice() throws Exception {
      ImportOrder importOrder = OrderMockData.validRecord();
      List<ImportOrderProduct> importOrderProducts = List.of(
              ImportOrderProduct.builder().quantity(10).importPrice(10000000L).productId(2).importOrder(importOrder).build()
      );
      importOrder.setImportOrderProducts(importOrderProducts);
      importOrder.setCode("TD123");
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(importOrder)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Giá nhập không được lớn hơn giá bán", message);
                });
    }

  @Test
  @DisplayName("Create a new order with a product have import price less than or equal to zero")
  @Transactional
  void createOrderWithImportPriceEqualToZero() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_importPriceEqualToZero("TD123"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Giá nhập phải lớn hơn 0", message);
                });
    }

  @Test
  @DisplayName("Create a new order with a product have quantity less than or equal to zero")
  @Transactional
  void createOrderWithQuantityEqualToZero() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_quantityEqualToZero("TD123"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Số lượng phải lớn hơn 0", message);
                });
    }

  @Test
  @DisplayName("Create a new order with a non-existing product")
  @Transactional
  void createOrderWithNonExistProduct() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistingProduct("TD123"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Sản phẩm không tồn tại", message);
                });
    }


  //    ==================== UPDATE ====================
  @Test
  @DisplayName("Update an existing order with valid data")
  @Transactional
  void updateOrderWithValidData() throws Exception {
    ImportOrder importOrder = OrderMockData.validRecord();
    importOrder.setCode("MH004");
    mockMvc.perform(put("/api/v1/orders/32")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(importOrder)))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Update an existing order with code changed")
  @Transactional
  void updateOrderWithCodeChanged() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.inValidRecord_codeChanged())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                  String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                  System.out.println("responseMessage: " + responseMessage);
                  JSONObject jsonResponse = new JSONObject(responseMessage);

                  // check status
                  Integer status = Integer.valueOf(jsonResponse.getString("status"));
                  assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                  // check message
                  String message = jsonResponse.getString("message");
                  assertEquals("Không được thay đổi mã đơn hàng", message);
                });
    }

  @Test
  @DisplayName("Update an existing order with missing data")
  @Transactional
  void updateOrderWithMissingData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(true);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/32")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingData())))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);

              // check code missing
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String code = dataObject.getString("code");
              if (code != null)
                  assertEquals("Đơn hàng cần phải có mã đơn hàng", code);

              String tax = dataObject.getString("tax");
              if (tax != null)
                  assertEquals("Đơn hàng cần phải có thuế", tax);

              String supplierId = dataObject.getString("supplierId");
              if (supplierId != null)
                  assertEquals("Đơn hàng cần phải có mã nhà cung cấp", supplierId);

              String employeeId = dataObject.getString("employeeId");
              if (employeeId != null)
                  assertEquals("Đơn hàng cần phải có mã nhân viên thực hiện", employeeId);
            });
  }

  @Test
  @DisplayName("Update an order with negative data")
  @Transactional
  void updateOrderWithNegativeData() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_negativeData("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("BAD_REQUEST", message);

                    // check data
                    JSONObject dataObject = jsonResponse.getJSONObject("data");
                    String tax = dataObject.getString("tax");
                    if (tax != null)
                        assertEquals("Thuế phải lớn hơn 0", tax);

                    String employeeId = dataObject.getString("employeeId");
                    if (employeeId != null)
                        assertEquals("Mã nhân viên thực hiện phải lớn hơn 0", employeeId);

                    String supplierId = dataObject.getString("supplierId");
                    if (supplierId != null)
                        assertEquals("Mã nhà cung cấp phải lớn hơn 0", supplierId);
                });
    }

  @Test
  @DisplayName("Update an order with non-existing supplier")
  @Transactional
  void updateOrderWithNonExistSupplier() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistSupplier("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Nhà cung cấp không tồn tại", message);
                });
    }

  @Test // 200 ?????????
  @DisplayName("Update an order with non-existing employee")
  @Transactional
  void updateOrderWithNonExistEmployee() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistEmployee("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Nhân viên không tồn tại", message);
                });
    }

  @Test
  @DisplayName("Update an order with empty list product")
  @Transactional
  void updateOrderWithEmptyOrderProduct() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingImportOrderProduct("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Đơn hàng cần phải có sản phẩm", message);
                });
    }

  @Test
  @DisplayName("Update an order with a product have import price less than or equal to zero")
  @Transactional
  void updateOrderWithImportPriceEqualToZero() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_importPriceEqualToZero("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Giá nhập phải lớn hơn 0", message);
                });
    }

  @Test
  @DisplayName("Update an order with a product have quantity less than or equal to zero")
  @Transactional
  void updateOrderWithQuantityEqualToZero() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_quantityEqualToZero("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Số lượng phải lớn hơn 0", message);
                });
    }

  @Test
  @DisplayName("Update an order with product have import price greater than sell price")
  @Transactional
  void udpateOrderWithProductImportPriceGreaterThanSellPrice() throws Exception {
        ImportOrder importOrder = OrderMockData.validRecord();
        List<ImportOrderProduct> importOrderProducts = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(10000000L).productId(2).importOrder(importOrder).build()
        );
        importOrder.setImportOrderProducts(importOrderProducts);
        importOrder.setCode("MH004");
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(importOrder)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Giá nhập không được lớn hơn giá bán", message);
                });
    }

  @Test
  @DisplayName("Update an order with a non-existing product")
  @Transactional
  void updateOrderWithNonExistProduct() throws Exception {
        mockMvc.perform(put("/api/v1/orders/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_nonExistingProduct("MH004"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Sản phẩm không tồn tại", message);
                });
    }

  @Test
  @DisplayName("Update a non-existing order")
  @Transactional
  void updateNonExistingOrder() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.validRecord())))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("Đơn hàng không tồn tại", message);
            });
  }

  @Test
  @DisplayName("Update an order was paid")
  @Transactional
  void updateOrderWasPaid() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
        mockMvc.perform(put("/api/v1/orders/38")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(OrderMockData.validRecord())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("responseMessage: " + responseMessage);
                    JSONObject jsonResponse = new JSONObject(responseMessage);

                    // check status
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    // check message
                    String message = jsonResponse.getString("message");
                    assertEquals("Đơn hàng không được sửa đổi ", message);
                });
    }

  @Test
  @DisplayName("Update status of an existing order")
  @Transactional
  void updateStatusOfExistingOrder() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(true);
//    when(orderService.updateStatus(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/38/pay")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Update status of a non-existing order")
  @Transactional
  void updateStatusOfNonExistingOrder() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.updateStatus(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/999/pay"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
                String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                System.out.println("responseMessage: " + responseMessage);
                JSONObject jsonResponse = new JSONObject(responseMessage);

                // check status
                Integer status = Integer.valueOf(jsonResponse.getString("status"));
                assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                // check message
                String message = jsonResponse.getString("message");
                assertEquals("Đơn hàng không tồn tại", message);
            });
  }



  //    ==================== DELETE ====================
  @Test
  @DisplayName("Delete an existing order")
  @Transactional
  void deleteExistingOrder() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(true);
//    when(orderService.delete(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(delete("/api/v1/orders/32"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Delete a non-existing order")
  @Transactional
  void deleteNonExistingOrder() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.delete(any(Integer.class))).thenReturn(ResponseEntity.notFound().build());
    mockMvc.perform(delete("/api/v1/orders/999"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              System.out.println("responseMessage: " + responseMessage);
              JSONObject jsonResponse = new JSONObject(responseMessage);

              // check status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // check message
              String message = jsonResponse.getString("message");
              assertEquals("Đơn hàng không tồn tại", message);
            });
  }


//  @Test
//  @DisplayName("hihi")
//  void hihi() throws Exception {
//    System.out.println("dung: " + orderService.getById(2));
//    when(orderService.getById(45)).thenReturn(ResponseEntity
//            .status(HttpStatus.BAD_REQUEST)
//            .body(new SystemResponse<>(400, "Invalid order ID")));
//
//    // Perform the API request with specific ID
//    mockMvc.perform(get("/api/v1/orders/45"))
//            .andExpect(status().isBadRequest()); // Mong đợi là 400 (Bad Request)
//
//  }
}