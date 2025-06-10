package woowaTable.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Name;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.Password;

public record SignupRequest(
        @Email String email,
        @NotBlank String password,
        @NotBlank String name
) {
    public Customer toCustomer() {
        return new Customer(
                new Name(name),
                new woowaTable.user.domain.Email(email),
                new Password(password)
        );
    }

    public Owner toOwner() {
        return new Owner(
                new Name(name),
                new woowaTable.user.domain.Email(email),
                new Password(password)
        );
    }
}
