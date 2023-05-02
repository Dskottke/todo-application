package com.example.backend;

import com.example.backend.model.ToDo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ToDoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    @DirtiesContext
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

    @Test
    @DisplayName("PUT - Request , with valid id, expect HTTP-status 200 and updated todo")
    @DirtiesContext
    void putWithValidIdExpectHttpStatus200AndUpdatedTodo() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "test",
                                    "title": "test"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ToDo toDo = objectMapper.readValue(result, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "IN_PROGRESS"
                        }
                        """));
    }

    @Test
    @DisplayName("PUT - Request , with invalid id, expect HTTP-status 404")
    @DirtiesContext
    void putWithInvalidIdExpectHttpStatus404() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/" + "123"))
                .andExpect(status().is(404))
                .andExpect(content().string("Could not find todo 123"));
    }

    @Test
    @DisplayName("PUT - Request , for ToDo with status DONE, expect HTTP-status 400")
    @DirtiesContext
    void putWithStatusDoneExpectHttpStatus400() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "test",
                                    "title": "test"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ToDo toDo = objectMapper.readValue(result, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "IN_PROGRESS"
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "DONE"
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/" + toDo.id()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid status"));


    }
}

