package com.example.backend.security.models;
import com.example.backend.security.Role;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Builder
@Document
public record AppUser(@Id String id, String username, String password, Role role, boolean confirmed) {
}
