package shh.alias.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alias.randommer")
public record RandommerProperties(
        String baseUrl,
        int connectionTimeout,
        int readTimeout,
        String secretKey
) {
}
