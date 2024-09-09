package com.learn.explorify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.explorify.model.Product;
import com.learn.explorify.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("GET /product/1 - Found")
    void testGetProductByIdFound() throws Exception {
        Product mockProduct = new Product(1, "keyboard", 700);
        doReturn(Optional.of(mockProduct)).when(productService).findById(1);
        mockMvc.perform(get("/product/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockProduct.id())))
                .andExpect(jsonPath("$.name", is(mockProduct.name())))
                .andExpect(jsonPath("$.price", is(mockProduct.price())));
    }

    @Test
    @DisplayName("GET /product/1 - Found")
    void testGetProductById_NotFound() throws Exception {
        doReturn(Optional.empty()).when(productService).findById(1);
        mockMvc.perform(get("/product/{id}", 1))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /product/ - created")
    void testPostProduct_created() throws Exception {
        Product mockProduct = new Product(1, "keyboard", 700);
        doReturn(mockProduct).when(productService).save(any());
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockProduct)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, is("/product/1")));
    }

    @Test
    @DisplayName(("DELETE /product/1 - success"))
    void testProductDelete_success() throws Exception {
        mockMvc.perform(delete("/product/{id}",1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(("DELETE /product/1 - notFound"))
    void testProductDelete_notFound() throws Exception {
        doReturn(-1).when(productService).delete(0);
        mockMvc.perform(delete("/product/{id}",0))
                .andExpect(status().isNotFound());
    }
}
