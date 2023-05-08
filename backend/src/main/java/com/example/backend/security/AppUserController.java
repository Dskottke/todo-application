package com.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public String login() {
        return "hello user";
    }

    @GetMapping("/me")
    public String me() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String create(@RequestBody NewUser newUser) {
        return appUserService.create(newUser);
    }
}
