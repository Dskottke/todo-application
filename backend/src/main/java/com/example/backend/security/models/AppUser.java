package com.example.backend.security.models;
import com.example.backend.security.Role;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record AppUser(String id, String username, String password, Role role, boolean confirmed) {
}
