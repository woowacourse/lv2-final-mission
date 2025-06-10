package finalmission.presentation.response;

import finalmission.domain.customer.Customer;

public record CustomerResponse(
        Long customerId,
        String email,
        String password,
        String name,
        String PhoneNumber
) {

    public static CustomerResponse fromCustomer(final Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getName(),
                customer.getPhoneNumber()
        );
    }
}
