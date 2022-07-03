package com.sirius.security.model;

import lombok.Data;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class User {
    private Set<String> roles = ConcurrentHashMap.newKeySet();
    private String name;
    private String passwordEncrypted;
    private String tokenKey;
}
