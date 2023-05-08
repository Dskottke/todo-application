package com.example.backend.security;

import com.example.backend.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final Utils utils;

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public String create(NewUser newUser) {

        if (usernameIsTaken(newUser.username())) {
            throw new UsernameIsTakenException("Username : " + newUser.username() + " is already taken.");
        }

        AppUser appUser = new AppUser(
                utils.getUUID(),
                newUser.username(),
                passwordEncoder.encode(newUser.password()));

        return appUserRepository.save(appUser).username();
    }

    private boolean usernameIsTaken(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

}
