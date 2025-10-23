package com.backend_java.Vijay_Mallay.domain.model;
// Local import
import com.backend_java.Vijay_Mallay.domain.enums.Gender;

// Jakarta persistence
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Transient;

// Lombok
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Validation 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Java time
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "customer")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "name", nullable = false, length = 50)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Transient // Calculated from DOB, not stored in DB
    private Long age;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 15, message = "Phone number must be at most 15 characters")
    private String phoneNo;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Date of Birth is mandatory")
    private LocalDate dob;

    @Transient
    public Integer getAge() {
        if (dob == null) return null;
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
