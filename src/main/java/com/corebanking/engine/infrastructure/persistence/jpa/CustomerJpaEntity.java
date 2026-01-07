package com.corebanking.engine.infrastructure.persistence.jpa;

import com.corebanking.engine.domain.model.enums.CustomerStatus;
import com.corebanking.engine.domain.model.enums.KycStatus;
import com.corebanking.engine.domain.model.enums.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;

    private LocalDate dob;

    private String aadhaar;
    private String pan;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "customer_accounts", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "account_id")
    private List<String> linkedAccounts;

    protected CustomerJpaEntity() {}

    // ===== getters & setters =====

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public CustomerStatus getStatus() { return status; }
    public void setStatus(CustomerStatus status) { this.status = status; }

    public KycStatus getKycStatus() { return kycStatus; }
    public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getAadhaar() { return aadhaar; }
    public void setAadhaar(String aadhaar) { this.aadhaar = aadhaar; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<String> getLinkedAccounts() { return linkedAccounts; }
    public void setLinkedAccounts(List<String> linkedAccounts) { this.linkedAccounts = linkedAccounts; }
}
