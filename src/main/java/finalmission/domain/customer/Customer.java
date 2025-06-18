package finalmission.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long customerId;
    private String email;
    private String password;
    private String name;
    private String PhoneNumber;
    @Enumerated(EnumType.STRING)
    private CustomerRole role;

    private Customer(final Long customerId,
                     final String email,
                     final String password,
                     final String name,
                     final String phoneNumber,
                     final CustomerRole role
    ) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.PhoneNumber = phoneNumber;
        this.role = role;
    }

    protected Customer() {
    }

    public static Customer register(String email, String password, String name, String phoneNumber) {
        return new Customer(null, email, password, name, phoneNumber, CustomerRole.CUSTOMER);
    }

    public static Customer ofExisting(Long customerId, String email, String password, String name, String phoneNumber,
                                      CustomerRole role) {
        return new Customer(customerId, email, password, name, phoneNumber, role);
    }

    public boolean matchesPassword(String password) {
        return this.password.equals(password);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public CustomerRole getRole() {
        return role;
    }
}
