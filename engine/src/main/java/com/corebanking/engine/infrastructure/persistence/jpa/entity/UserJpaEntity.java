package com.corebanking.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean enabled;

    // 🔥 Link to customer
    @Column(name = "customer_id")
    private String customerId;

    protected UserJpaEntity() {}

    public UserJpaEntity(String id, String email, String password, String role, boolean enabled) {
        this.id = id;
        this.email = email.trim().toLowerCase(); // ✅ normalize
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    // ================= GETTERS =================
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean isEnabled() { return enabled; }
    public String getCustomerId() { return customerId; }

    // ================= SETTERS =================
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase(); // ✅ normalize
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}