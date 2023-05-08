package com.example.backend.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AppUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET - Request , expect HTTP-status 200 and the new ToDo")
    @WithMockUser()
    void validLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello user"));
    }

    @Test
    @DisplayName("GET - Request , expect HTTP-status 200 and the current authenticated username")
    @WithMockUser(username = "user")
    void meAndExpectMockUserUsernameUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    @DirtiesContext
    @DisplayName("POST - Request , expect HTTP-status 201 and the new user")
    void createAndExpectHttpStatus201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .content("""
                                {
                                    "username": "user",
                                    "password": "paSsword1!"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string("user"));
    }

    @Test
    @DirtiesContext
    @DisplayName("POST - Request , expect HTTP-status 400 and the Username that already exists")
    void createAndExpectHttpStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .content("""
                                {
                                    "username": "user",
                                    "password": "paSsword1!"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string("user"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .content("""
                                {
                                    "username": "user",
                                    "password": "paSsword1!"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username : " + "user" + " is already taken."));
    }

    @Test
    @DirtiesContext
    @DisplayName("POST - Request , expect HTTP-status 400 and the Password that is not valid")
    void createAndExpectHttpStatus400PasswordNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .content("""
                                {
                                    "username": "user",
                                    "password": "password"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Password must contain at least 8 characters, one uppercase letter, one digit and one special character."));
    }
}
