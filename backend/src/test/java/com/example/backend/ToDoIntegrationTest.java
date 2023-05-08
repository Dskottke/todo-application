package com.example.backend;

import com.example.backend.todos.ToDo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ToDoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    @DisplayName("GET - Request , expect empty list and HTTP-status 200")
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void getAllTodosAndExpectEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @Test
    @DisplayName("POST - Request , expect HTTP-status 201 and the new ToDo")
    @DirtiesContext
    @WithMockUser
    void postNewToDoDTOAndExpectStatus201AndToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "testDescription",
                                    "title": "testTitle",
                                     "dueDate" : "2021-01-01"
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
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    @DisplayName("POST - Request , with null values, expect HTTP-status 400")
    @WithMockUser
    void postNewToDoDTOWithNullValuesExpectBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
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
    @WithMockUser
    void postNewToDoDTOWithEmptyStringValuesExpectBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "",
                                    "title": "",
                                    "dueDate" : "2021-01-01"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Title and description cannot be empty"));
    }

    @Test
    @DisplayName("PUT - Request , with valid id, expect HTTP-status 200 and updated todo")
    @DirtiesContext
    @WithMockUser
    void putWithValidIdExpectHttpStatus200AndUpdatedTodo() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "test",
                                    "title": "test",
                                    "dueDate" : "2021-01-01"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ToDo toDo = objectMapper.readValue(result, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "IN_PROGRESS"
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    @DisplayName("PUT - Request , with invalid id, expect HTTP-status 404")
    @DirtiesContext
    @WithMockUser
    void putWithInvalidIdExpectHttpStatus404() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + "123"))
                .andExpect(status().is(404))
                .andExpect(content().string("Could not find todo 123"));
    }

    @Test
    @DisplayName("PUT - Request , for ToDo with status DONE, expect HTTP-status 400")
    @DirtiesContext
    @WithMockUser
    void putWithStatusDoneExpectHttpStatus400() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "test",
                                    "title": "test",
                                     "dueDate" : "2021-01-01"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ToDo toDo = objectMapper.readValue(result, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "IN_PROGRESS"
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + toDo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "title": "test",
                            "status": "DONE"
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + toDo.id()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid status"));


    }

    @Test
    @DisplayName("DELETE - Request , with valid id, expect HTTP-status 200")
    @DirtiesContext
    @WithMockUser
    void deleteWithValidIdExpectHttpStatus200() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                {
                                    "description": "test",
                                    "title": "test",
                                    "dueDate" : "2021-01-01"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ToDo toDo = objectMapper.readValue(result, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + toDo.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE - Request , with invalid id, expect HTTP-status 404")
    @WithMockUser
    void deleteWithInvalidIdExpectHttpStatus404() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + "123"))
                .andExpect(status().is(404))
                .andExpect(content().string("Could not find todo 123"));
    }

    @Test
    @DisplayName("GET - Request - without MockUser, expect HTTP-status 401")
    void getWithoutMockUserExpectHttpStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isUnauthorized());
    }
}

