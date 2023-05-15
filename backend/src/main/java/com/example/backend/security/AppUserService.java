package com.example.backend.security;

import com.example.backend.Utils;
import com.example.backend.security.exceptions.PasswordValidationException;
import com.example.backend.security.exceptions.UsernameIsTakenException;
import com.example.backend.security.models.AppUser;
import com.example.backend.security.models.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<AppUser> confirmList() {
        return appUserRepository.getAllUsersByConfirmedIsFalse();
    }

    public AppUser getCurrentUser() {
        Set<String> roles = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMIN")) {
            return AppUser.builder()
                    .username(
                            SecurityContextHolder.getContext().getAuthentication().getName())
                    .role(Role.ADMIN)
                    .confirmed(true).build();
        } else {
            return AppUser.builder()
                    .username(
                            SecurityContextHolder.getContext().getAuthentication().getName())
                    .role(Role.USER)
                    .confirmed(true).build();
        }

    }

    public void delete(String id) {
        appUserRepository.deleteById(id);
    }

    public AppUser confirmUser(String id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id : " + id + " does not exist.")
        );

        return appUserRepository.save(
                AppUser.builder()
                        .id(appUser.id())
                        .username(appUser.username())
                        .password(appUser.password())
                        .role(appUser.role())
                        .confirmed(true).build());
    }
}
