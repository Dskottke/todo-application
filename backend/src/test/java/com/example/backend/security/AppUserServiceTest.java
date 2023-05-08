package com.example.backend.security;

import com.example.backend.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final Utils utils = mock(Utils.class);
    private final AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder, utils);

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

    @Test
    @DisplayName("create - should return the created AppUser")
    void createShouldReturnTheCreatedAppUser() {
        //GIVEN
        NewUser newUser = new NewUser("user", "password");
        String uuid = "1";
        String encodedPassword = "123";
        AppUser expected = new AppUser(uuid, newUser.username(), encodedPassword);
        //WHEN
        when(utils.getUUID()).thenReturn(uuid);
        when(passwordEncoder.encode(newUser.password())).thenReturn(encodedPassword);
        when(appUserRepository.save(
                new AppUser(
                        uuid,
                        newUser.username(),
                        encodedPassword)))
                .thenReturn(expected);
        String actual = appUserService.create(newUser);
        //THEN
        verify(utils).getUUID();
        verify(passwordEncoder).encode(newUser.password());
        verify(appUserRepository).save(
                expected);
        assertEquals(expected.username(), actual);
    }

    @Test
    @DisplayName("create - should throw UsernameIsTakenException")
    void createShouldThrowUsernameIsTakenException() {
        //GIVEN
        NewUser newUser = new NewUser("user", "password");
        //WHEN
        when(appUserRepository.findByUsername(newUser.username())).thenReturn(Optional.of(new AppUser("1", "user", "password")));
        //THEN
        UsernameIsTakenException exception = assertThrows(UsernameIsTakenException.class, () -> appUserService.create(newUser));
        assertEquals("Username : " + newUser.username() + " is already taken.", exception.getMessage());
    }

}