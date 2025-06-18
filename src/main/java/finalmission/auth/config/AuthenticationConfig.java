package finalmission.auth.config;

import finalmission.auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expire-length}") Long validityInMilliseconds
    ) {
        return new JwtTokenProvider(secretKey, validityInMilliseconds);
    }
}
