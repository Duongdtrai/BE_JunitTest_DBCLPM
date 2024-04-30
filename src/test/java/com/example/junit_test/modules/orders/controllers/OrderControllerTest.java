package com.example.junit_test.modules.orders.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.OrderMockData;
import com.example.junit_test.modules.orders.entities.ImportOrder;
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
    SystemResponse<ResponsePage<ImportOrder>> systemResponse = new SystemResponse<>();
    when(orderService.list(any(Integer.class), any(Integer.class), any(Boolean.class), any(String.class))).thenReturn(ResponseEntity.ok(systemResponse));
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
  @Test
  @DisplayName("Get an existing order by ID")
  void getExistingOrderById() throws Exception {
//    SystemResponse<ImportOrder> systemResponse = new SystemResponse<>();
//    systemResponse.setData(importOrder);
//    // Mock data cho cả hai trường hợp ID = 1 và ID = 45
//    when(orderService.getById(45)).thenReturn(ResponseEntity
//            .status(HttpStatus.OK)
//            .body(new SystemResponse<>(404, "123")));
    mockMvc.perform(get("/api/v1/orders/45"))
            .andExpect(status().isBadRequest());
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
              assertEquals("Đơn đặt hàng không tồn tại", message);
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
            });

  }

  //    ==================== CREATE ====================
  // check validation
  @Test
  @DisplayName("Create a new order with valid data success")
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
  @DisplayName("Create a new order with invalid data failed")
  @Transactional
  void createOrderWithInvalidData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.create(any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingCode())))
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
                assertEquals("Code is not empty", code);
            });
  }

  //    ==================== UPDATE ====================
  @Test
  @DisplayName("Update an existing order with invalid data")
  void updateOrderWithInvalidData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(false);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.validRecord())))
            .andExpect(status().isOk());
  }


  @Test
  @DisplayName("Update an existing order with valid data")
  void updateOrderWithValidData() throws Exception {
//    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
//    systemResponse.setData(true);
//    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(OrderMockData.invalidRecord_missingCode())))
            .andExpect(status().isOk());
  }


  @Test
  @DisplayName("Update a non-existing order")
  void updateNonExistingOrder() throws Exception {
    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
    systemResponse.setData(false);
    when(orderService.update(any(Integer.class), any(ImportOrder.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(importOrder)))
            .andExpect(status().isOk());
  }


  @Test
  @DisplayName("Update status of an existing order")
  void updateStatusOfExistingOrder() throws Exception {
    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
    systemResponse.setData(true);
    when(orderService.updateStatus(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/1/pay"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Update status of a non-existing order")
  void updateStatusOfNonExistingOrder() throws Exception {
    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
    systemResponse.setData(false);
    when(orderService.updateStatus(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(put("/api/v1/orders/999/pay"))
            .andExpect(status().isOk());
  }

  //    ==================== DELETE ====================
  @Test
  @DisplayName("Delete an existing order")
  void deleteExistingOrder() throws Exception {
    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
    systemResponse.setData(true);
    when(orderService.delete(any(Integer.class))).thenReturn(ResponseEntity.ok(systemResponse));
    mockMvc.perform(delete("/api/v1/orders/1"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Delete a non-existing order")
  void deleteNonExistingOrder() throws Exception {
    SystemResponse<Boolean> systemResponse = new SystemResponse<>();
    systemResponse.setData(false);
    when(orderService.delete(any(Integer.class))).thenReturn(ResponseEntity.notFound().build());
    mockMvc.perform(delete("/api/v1/orders/999"))
            .andExpect(status().isNotFound());
  }


  @Test
  @DisplayName("hihi")
  void hihi() throws Exception {
    System.out.println("dung: " + orderService.getById(2));
//    when(orderService.getById(45)).thenReturn(ResponseEntity
//            .status(HttpStatus.BAD_REQUEST)
//            .body(new SystemResponse<>(400, "Invalid order ID")));

    // Perform the API request with specific ID
    mockMvc.perform(get("/api/v1/orders/45"))
            .andExpect(status().isBadRequest()); // Mong đợi là 400 (Bad Request)

  }
}