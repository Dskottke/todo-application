package com.example.backend.security;

import com.example.backend.security.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    @Query(value = "{'confirmed': false}", fields = "{'id' : 1, 'username' : 1, 'role' : 1, 'confirmed' : 1}")
    List<AppUser> getAllUsersByConfirmedIsFalse();
}
