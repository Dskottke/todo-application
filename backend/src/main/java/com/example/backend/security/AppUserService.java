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

        passwordValidation(newUser.password());

        AppUser appUser = new AppUser(
                utils.getUUID(),
                newUser.username(),
                passwordEncoder.encode(newUser.password()));

        return appUserRepository.save(appUser).username();
    }

    private boolean usernameIsTaken(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    private void passwordValidation(String password) {
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean isLongEnough = password.length() >= 8;
        boolean hasSpecialChar = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        if (!hasUppercase || !isLongEnough || !hasSpecialChar || !hasDigit) {
            throw new PasswordValidationException("Password must contain at least 8 characters, one uppercase letter, one digit and one special character.");
        }
    }
}
