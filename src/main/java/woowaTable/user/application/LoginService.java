package woowaTable.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.common.exception.error.BadRequestException;
import woowaTable.user.application.dto.LoginCheckRequest;
import woowaTable.user.application.dto.LoginCheckResponse;
import woowaTable.user.application.dto.LoginRequest;
import woowaTable.user.application.dto.SignupRequest;
import woowaTable.user.application.dto.Token;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.User;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtHandler jwtHandler;
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public Token loginCustomer(final LoginRequest request) {
        final Customer customer = userQueryService.findCustomerByEmailAndPassword(request.email(), request.password());
        return jwtHandler.createToken(customer);
    }

    public Token loginOwner(final LoginRequest request) {
        final Owner owner = userQueryService.findOwnerByEmailAndPassword(request.email(), request.password());
        return jwtHandler.createToken(owner);
    }

    public LoginCheckResponse checkLogin(final LoginCheckRequest request) {
        final User user = userQueryService.findByIdAndRole(request);
        return LoginCheckResponse.from(user);
    }

    public LoginCheckResponse signupCustomer(final SignupRequest request) {
        final Customer customer = request.toCustomer();
        validateDuplicatedCustomer(customer);
        final Customer savedCustomer = userCommandService.saveCustomer(customer);
        return LoginCheckResponse.from(savedCustomer);
    }

    public LoginCheckResponse signupOwner(final SignupRequest request) {
        final Owner owner = request.toOwner();
        validateDuplicatedOwner(owner);
        final Owner savedCustomer = userCommandService.saveOwner(owner);
        return LoginCheckResponse.from(savedCustomer);
    }

    private void validateDuplicatedCustomer(final Customer customer) {
        if (userQueryService.existsCustomerByEmail(customer.getEmail())) {
            throw new BadRequestException(("이미 존재하는 손님입니다."));
        }
    }

    private void validateDuplicatedOwner(final Owner owner) {
        if (userQueryService.existsOwnerByEmail(owner.getEmail())) {
            throw new BadRequestException(("이미 존재하는 손님입니다."));
        }
    }
}
