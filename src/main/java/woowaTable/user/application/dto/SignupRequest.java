package woowaTable.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.Password;
import woowaTable.user.domain.UserName;

public record SignupRequest(
        @Email String email,
        @NotBlank String password,
        @NotBlank String name
) {
    public Customer toCustomer() {
        return new Customer(
                new UserName(name),
                new woowaTable.user.domain.Email(email),
                new Password(password)
        );
    }

    public Owner toOwner() {
        return new Owner(
                new UserName(name),
                new woowaTable.user.domain.Email(email),
                new Password(password)
        );
    }
}
