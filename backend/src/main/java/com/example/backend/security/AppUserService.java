package com.example.backend.security;

import com.example.backend.Utils;
import com.example.backend.security.exceptions.PasswordValidationException;
import com.example.backend.security.exceptions.UsernameIsTakenException;
import com.example.backend.security.models.AppUser;
import com.example.backend.security.models.ConfirmUser;
import com.example.backend.security.models.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
                passwordEncoder.encode(newUser.password()),
                Role.USER,
                false);

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

    public List<ConfirmUser> confirmList() {
        List<AppUser> unconfirmedAppUsers = appUserRepository.getAllUsersByConfirmedIsFalse();
        return List.of(unconfirmedAppUsers.stream().map(appUser -> new ConfirmUser(appUser.id(), appUser.username(), appUser.role())).toArray(ConfirmUser[]::new));
    }
}
