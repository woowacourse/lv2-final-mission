package finalmission.customer.controller.dto.response;

import finalmission.customer.entity.Customer;

public record CustomerCreateResponse(
        String nickName,
        String email
) {

    public static CustomerCreateResponse of (Customer customer) {
        return new CustomerCreateResponse(
                customer.getNickName(),
                customer.getEmail()
        );
    }
}
