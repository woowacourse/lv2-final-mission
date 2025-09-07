package lavatoryreservation.external.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider(@Value("${jwt.secret_key}") String secretKey) {
        return new JwtTokenProvider(secretKey);
    }
}

