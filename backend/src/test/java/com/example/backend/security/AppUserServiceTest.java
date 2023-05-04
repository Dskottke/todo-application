package com.example.backend.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final AppUserService appUserService = new AppUserService(appUserRepository);

    @Test
    @DisplayName("findByUsername - should return the AppUser with the given username")
    void findByUsername() {
        //GIVEN
        String username = "user";
        //WHEN
        Optional<AppUser> expected = Optional.of(new AppUser("1", username, "password"));
        when(appUserRepository.findByUsername(username)).thenReturn(expected);
        Optional<AppUser> actual = appUserService.findByUsername("user");
        //THEN
        verify(appUserRepository).findByUsername(username);
        assertEquals(expected, actual);
    }

}