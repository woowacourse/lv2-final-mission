package woowaTable.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.user.application.dto.LoginRequest;
import woowaTable.user.application.dto.Token;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Owner;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtHandler jwtHandler;
    private final UserQueryService userQueryService;

    public Token customerLogin(final LoginRequest request) {
        final Customer customer = userQueryService.findCustomerByEmailAndPassword(request.email(), request.password());
        return jwtHandler.createToken(customer);
    }

    public Token ownerLogin(final LoginRequest request) {
        final Owner owner = userQueryService.findOwnerByEmailAndPassword(request.email(), request.password());
        return jwtHandler.createToken(owner);
    }
}
