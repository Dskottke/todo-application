package com.example.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private final AppUserService appUserService = mock(AppUserService.class);
    private final UserDetailsService userDetailsService = new SecurityConfig(appUserService).userDetailsService();

    @Test
    void userDetailsService() {
        //GIVEN

        //WHEN
        User expected = (User) User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        when(appUserService.findByUsername("user")).thenReturn(
                java.util.Optional.of(
                        new AppUser(
                                "1",
                                "user",
                                "password",
                                Role.USER,
                                true)));
        User actual = (User) userDetailsService.loadUserByUsername("user");
        //THEN
        verify(appUserService).findByUsername("user");
        assertEquals(expected, actual);
    }
}