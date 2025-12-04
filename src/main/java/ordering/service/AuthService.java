package ordering.service;

import ordering.dto.request.LoginCheck;
import ordering.entity.User;
import ordering.provider.JwtTokenProvider;
import ordering.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserJpaRepository userJpaRepository, JwtTokenProvider jwtTokenProvider) {
        this.userJpaRepository = userJpaRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(LoginCheck request) {
        User user = userJpaRepository.findByName(request.name())
            .orElseThrow(IllegalArgumentException::new);

        return jwtTokenProvider.createToken(user);
    }

    public User findUserByToken(String token) {
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        return userJpaRepository.findById(userId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
