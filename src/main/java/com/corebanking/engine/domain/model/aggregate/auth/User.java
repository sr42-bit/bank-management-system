package com.corebanking.engine.domain.model.aggregate.auth;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final String id;
    private final String email;
    private String password;
    private final String role;
    private boolean enabled;

    public User(String email, String password, String role) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(role);

        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean isEnabled() { return enabled; }

    public void disable() {
        this.enabled = false;
    }
}