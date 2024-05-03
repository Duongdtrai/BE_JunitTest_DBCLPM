package com.example.junit_test.modules.suppliers.controllers;

import com.example.junit_test.modules.SupplierMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupplierControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  //    ==================== GET LIST ====================
  @Test
  @DisplayName("Get list suppliers")
  public void listSuppliers() throws Exception {
    mockMvc.perform(get("/api/v1/suppliers"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get list suppliers with page and size")
  public void listSuppliersWithPageAndSize() throws Exception {
    mockMvc.perform(get("/api/v1/suppliers?page=1&size=20"))
            .andExpect(status().isOk());
  }

  //    ==================== GET BY ID ====================
  @Test
  @DisplayName("Get an existing supplier by ID")
  public void getExistingSupplierById() throws Exception {
    mockMvc.perform(get("/api/v1/suppliers/11"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get a non-existing supplier by ID")
  public void getNonExistingSupplierById() throws Exception {
    mockMvc.perform(get("/api/v1/suppliers/999"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              String message = jsonResponse.getString("message");
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              assertEquals("Supplier is not exist", message);
            });
  }


  //    ==================== CREATE ====================

  @Test
  @DisplayName("Create a new supplier with validation")
  @Transactional
  public void createSupplierWithValidation() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.validSupplier()))
            )
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Create a new supplier with validation same email and address and phone number")
  @Transactional
  public void createSupplierWithSameEmailAndAddressAndPhoneNumber() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.validSupplierSameNameAndAddressAndPhoneNumber()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("Supplier is exist name and address and phone number", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              String data = jsonResponse.getString("data");
              assertNull(data);
            });
  }

  @Test
  @DisplayName("Create a new supplier with validation missing email")
  @Transactional
  public void createSupplierWithValidationMissingEmail() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingEmail()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String email = dataObject.getString("email");
              assertEquals("Email is required", email);
            });

  }


  @Test
  @DisplayName("Create a new supplier with validation missing name")
  @Transactional
  public void createSupplierWithValidationMissingName() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingName()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String name = dataObject.getString("name");
              assertEquals("Name is required", name);
            });

  }

  @Test
  @DisplayName("Create a new supplier with validation missing tax code")
  @Transactional
  public void createSupplierWithValidationMissingTaxCode() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingTaxCode()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String taxCode = dataObject.getString("taxCode");
              assertEquals("TaxCode is required", taxCode);
            });

  }


  @Test
  @DisplayName("Create a new supplier with validation missing address")
  @Transactional
  public void createSupplierWithValidationMissingAddress() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingAddress()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String address = dataObject.getString("address");
              assertEquals("Address is required", address);
            });
  }

  @Test
  @DisplayName("Create a new supplier with validation missing phone number")
  @Transactional
  public void createSupplierWithValidationMissingPhoneNumber() throws Exception {
    mockMvc.perform(post("/api/v1/suppliers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingPhoneNumber()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String phoneNumber = dataObject.getString("phoneNumber");
              assertEquals("PhoneNumber is required", phoneNumber);
            });
  }


//  @Test
//  @DisplayName("Create a new supplier with validation missing note")
//  @Transactional
//  public void createSupplierWithValidationMissingNote() throws Exception {
//    mockMvc.perform(post("/api/v1/suppliers")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingPhoneNumber()))
//            )
//            .andExpect(status().isBadRequest());
//  }


  @Test
  @DisplayName("Create a new supplier with validation missing phone number")
  @Transactional
  public void createSupplierWithValidationEmailAndPhoneNumber() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_phoneNumberAndEmail()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String phoneNumber = dataObject.getString("phoneNumber");
              if (phoneNumber != null) {
                assertEquals("Phone number format is not correct", phoneNumber);
              }
              String email = dataObject.getString("email");
              if (phoneNumber != null) {
                assertEquals("Email format is not correct", email);
              }
            });
  }


  //    ==================== UPDATE ====================

  @Test
  @DisplayName("Update a new supplier with validation")
  @Transactional
  public void updateSupplierWithValidation() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.validSupplierUpdate()))
            )
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Update a new supplier with validation id")
  @Transactional
  public void updateSupplierWithValidationId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/1000")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.validSupplierUpdate()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("Supplier is not exist", message);
              // data
              String data = jsonResponse.getString("data");
              assertNull(data);
            });
  }

  @Test
  @DisplayName("Update a new supplier with validation same email and address and phone number")
  @Transactional
  public void updateSupplierWithSameEmailAndAddressAndPhoneNumber() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.validSupplierSameNameAndAddressAndPhoneNumber()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("Supplier is exist name and address and phone number", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              String data = jsonResponse.getString("data");
              assertNull(data);
            });
  }

  @Test
  @DisplayName("Update a new supplier with validation missing email")
  @Transactional
  public void updateSupplierWithValidationMissingEmail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingEmail()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String email = dataObject.getString("email");
              assertEquals("Email is required", email);
            });

  }


  @Test
  @DisplayName("Update a new supplier with validation missing name")
  @Transactional
  public void updateSupplierWithValidationMissingName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingName()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String name = dataObject.getString("name");
              assertEquals("Name is required", name);
            });

  }

  @Test
  @DisplayName("Update a new supplier with validation missing tax code")
  @Transactional
  public void updateSupplierWithValidationMissingTaxCode() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingTaxCode()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String taxCode = dataObject.getString("taxCode");
              assertEquals("TaxCode is required", taxCode);
            });

  }


  @Test
  @DisplayName("Update a new supplier with validation missing address")
  @Transactional
  public void updateSupplierWithValidationMissingAddress() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingAddress()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String address = dataObject.getString("address");
              assertEquals("Address is required", address);
            });
  }

  @Test
  @DisplayName("Update a new supplier with validation missing phone number")
  @Transactional
  public void updateSupplierWithValidationMissingPhoneNumber() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingPhoneNumber()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String phoneNumber = dataObject.getString("phoneNumber");
              assertEquals("PhoneNumber is required", phoneNumber);
            });
  }


//  @Test
//  @DisplayName("Update a new supplier with validation missing note")
//  @Transactional
//  public void updateSupplierWithValidationMissingNote() throws Exception {
//    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_missingPhoneNumber()))
//            )
//            .andExpect(status().isBadRequest());
//  }


  @Test
  @DisplayName("Update a new supplier with validation missing phone number")
  @Transactional
  public void updateSupplierWithValidationEmailAndPhoneNumber() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/suppliers/11")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(SupplierMockData.invalidRecord_phoneNumberAndEmail()))
            )
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("BAD_REQUEST", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // data
              JSONObject dataObject = jsonResponse.getJSONObject("data");
              String phoneNumber = dataObject.getString("phoneNumber");
              if (phoneNumber != null) {
                assertEquals("Phone number format is not correct", phoneNumber);
              }
              String email = dataObject.getString("email");
              if (phoneNumber != null) {
                assertEquals("Email format is not correct", email);
              }
            });
  }


  //    ==================== DELETE ====================

  @Test
  @DisplayName("Delete a supplier with validation by id and status is false")
  @Transactional
  public void deleteSupplierById() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/suppliers/11"))
            .andExpect(status().isOk())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("SUCCESS", message);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.OK.value(), status);
              // data
              Boolean data = Boolean.valueOf(jsonResponse.getString("data"));
              assertTrue(data);
            });
  }

  @Test
  @DisplayName("Delete a supplier with validation by id not exist and status is false or true")
  @Transactional
  public void deleteSupplierByIdNotExist() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/suppliers/1000"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
              JSONObject jsonResponse = new JSONObject(responseMessage);
              // status
              Integer status = Integer.valueOf(jsonResponse.getString("status"));
              assertEquals(HttpStatus.BAD_REQUEST.value(), status);
              // message
              String message = jsonResponse.getString("message");
              assertEquals("Supplier is not exist", message);
              // data
              String data = jsonResponse.getString("data");
              assertNull(data);
            });
  }

}
