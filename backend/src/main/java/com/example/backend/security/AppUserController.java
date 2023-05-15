package com.example.backend.security;

import com.example.backend.security.models.AppUser;
import com.example.backend.security.models.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public AppUser me() {
        return appUserService.getCurrentUser();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String create(@RequestBody NewUser newUser) {
        return appUserService.create(newUser);
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "logout";
    }

    @GetMapping("/confirm/list")
    public List<AppUser> confirmList() {
        return appUserService.confirmList();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        appUserService.delete(id);
    }

    @PutMapping("/confirm/{id}")
    public void confirm(@PathVariable String id) {
        appUserService.confirmUser(id);
    }
}
