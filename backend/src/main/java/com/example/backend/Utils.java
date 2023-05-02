package com.example.backend;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Utils {

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
