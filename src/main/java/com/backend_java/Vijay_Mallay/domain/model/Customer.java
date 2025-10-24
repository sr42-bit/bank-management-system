//package declaration
package com.backend_java.Vijay_Mallay.domain.model;

//local import 
import com.backend_java.Vijay_Mallay.domain.enums.Gender;

//lombok imports
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// java time imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

// java util imports
import java.util.UUID;


// POJO CLASS — core business model

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    // Unique business ID (like CUST-UUID)
    private String customerId;

    private String fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String panNumber;
    private String aadharNumber;
    private boolean kycVerified;

    // Address fields
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Active status
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    /** Calculates age dynamically based on dateOfBirth */
    public Integer getAge() {
        if (dateOfBirth == null) return null;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /** Domain utility: generate a custom ID like CUST-UUID */
    public static String generateCustomerId() {
        return "BOB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
