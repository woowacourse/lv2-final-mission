package finalmission.application;

import finalmission.domain.auth.AuthenticationInfo;
import finalmission.domain.auth.AuthenticationTokenHandler;
import finalmission.domain.customer.Customer;
import finalmission.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationTokenHandler tokenHandler;
    private final CustomerRepository customerRepository;

    public AuthenticationService(final AuthenticationTokenHandler tokenHandler,
                                 final CustomerRepository customerRepository
    ) {
        this.tokenHandler = tokenHandler;
        this.customerRepository = customerRepository;
    }

    public String issueToken(final String email, final String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 틀렸습니다."));

        if (!customer.matchesPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        AuthenticationInfo authenticationInfo = new AuthenticationInfo(customer.getCustomerId(), customer.getRole());
        return tokenHandler.createToken(authenticationInfo);
    }

    public Customer getCustomerByToken(final String token) {
        boolean isValidToken = tokenHandler.isValidToken(token);

        if (!isValidToken) {
            throw new IllegalArgumentException("토큰이 만료되었거나 유효하지 않습니다.");
        }

        long id = tokenHandler.extractId(token);

        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다. 다시 로그인 해주세요."));
    }
}
