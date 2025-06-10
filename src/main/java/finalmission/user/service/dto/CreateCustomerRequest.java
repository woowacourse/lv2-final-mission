package finalmission.user.service.dto;

import finalmission.user.domain.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateCustomerRequest(
        @NotEmpty
        String name,
        @Email
        String email,
        @NotEmpty
        String password
) {
    public Customer toEntity() {
        return new Customer(name, email, password);
    }
}
