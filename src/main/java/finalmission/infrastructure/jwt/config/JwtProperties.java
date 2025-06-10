package finalmission.infrastructure.jwt.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String secretKey;

    public JwtProperties(
        final String secretKey
    ) {
        this.secretKey = secretKey;
    }
}
