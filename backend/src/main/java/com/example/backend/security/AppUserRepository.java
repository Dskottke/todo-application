package com.example.backend.security;

import com.example.backend.security.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    List<AppUser> getAllUsersByConfirmedIsFalse();
}
