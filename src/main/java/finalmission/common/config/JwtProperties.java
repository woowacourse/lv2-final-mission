package finalmission.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtProperties {

    private String secretKey;
    private Long expireLength;

    public JwtProperties(final String secretKey, final Long expireLength) {
        this.secretKey = secretKey;
        this.expireLength = expireLength;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Long getExpireLength() {
        return expireLength;
    }
}
