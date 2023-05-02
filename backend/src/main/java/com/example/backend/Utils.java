package com.example.backend;

import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class Utils {

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
