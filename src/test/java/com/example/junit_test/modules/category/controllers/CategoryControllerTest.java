package com.example.junit_test.modules.category.controllers;

import com.example.junit_test.modules.ProductMockData;
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
public class CategoryControllerTest {

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
    public void listCategories() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get an existing category by ID")
    public void getExistingCategoryById() throws Exception {
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get a non-existing category by ID")
    public void getNonExistingCategoryById() throws Exception {
        mockMvc.perform(get("/api/v1/category/999"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseMessage = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    JSONObject jsonResponse = new JSONObject(responseMessage);
                    String message = jsonResponse.getString("message");
                    Integer status = Integer.valueOf(jsonResponse.getString("status"));
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                });
    }

    @Test
    @DisplayName("Create a new category with validation")
    @Transactional
    public void createCategoryWithValidation() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ProductMockData.validProduct()))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update a new category with validation")
    @Transactional
    public void updateCategoryWithValidation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ProductMockData.validProduct()))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete a category with validation by id and status is false")
    @Transactional
    public void deleteCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/1"))
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
}