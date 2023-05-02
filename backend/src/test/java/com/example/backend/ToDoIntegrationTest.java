package com.example.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ToDoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET - Request , expect empty list and HTTP-status 200")
    void getAllTodosAndExpectEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }
    @Test
    @DisplayName("POST - Request , expect HTTP-status 201 and the new ToDo")
    void postNewToDoDTOAndExpectStatus201AndToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                .contentType("application/json")
                .content("""
                {
                    "description": "testDescription",
                    "title": "testTitle"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "description": "testDescription",
                            "title": "testTitle",
                            "status": "OPEN"
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
    @Test
    @DisplayName("POST - Request , with null values, expect HTTP-status 400")
    void postNewToDoDTOWithNullValuesExpectBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType("application/json")
                        .content("""
                {
                    "description": null,
                    "title": null
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST - Request , with empty String values, expect HTTP-status 400")
    void postNewToDoDTOWithEmptyStringValuesExpectBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType("application/json")
                        .content("""
                {
                    "description": "",
                    "title": ""
                }
                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Title and description cannot be empty"));
    }
}