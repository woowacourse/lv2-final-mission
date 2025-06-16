package finalmission.customer.service;

import finalmission.customer.controller.dto.request.TokenLoginCreateRequest;
import finalmission.customer.controller.dto.response.TokenLoginResponse;
import finalmission.customer.entity.Customer;
import finalmission.customer.infrastructure.JwtTokenProvider;
import finalmission.customer.repository.CustomerJpaRepository;
import finalmission.customer.resolver.UnauthenticatedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String TOKEN = "token";

    private final CustomerJpaRepository customerJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerJpaRepository customerJpaRepository, JwtTokenProvider jwtTokenProvider) {
        this.customerJpaRepository = customerJpaRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenLoginResponse tokenLogin(TokenLoginCreateRequest tokenLoginCreateRequest) {
        String email = tokenLoginCreateRequest.email();
        String password = tokenLoginCreateRequest.password();

        if (customerJpaRepository.existsByEmailAndPassword(email, password)) {
            String token = jwtTokenProvider.createToken(email);
            return new TokenLoginResponse(token);
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 회원입니다. 아이디와 비밀번호를 확인해 주세요.");
    }

    public Customer findCustomerByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return customerJpaRepository.findByEmail(payload)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 회원입니다. 아이디와 비밀번호를 확인해 주세요."));
    }

    public String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthenticatedException("[ERROR] 정상적인 접근이 아닙니다.");
        }

        String token = extractTokenFromCookie(cookies);

        if (token == null) {
            throw  new UnauthenticatedException("[ERROR] 정상적인 접근이 아닙니다.");
        }

        return token;
    }

    private String extractTokenFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (TOKEN.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
