package finalmission.infrastructure.client.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "kasi")
public class KasiProperties {

    private final String baseUrl;
    private final String secretKey;
    private final int connectTimeout;
    private final int readTimeout;

    public KasiProperties(
        final String baseUrl,
        final String secretKey,
        final int connectTimeout,
        final int readTimeout
    ) {
        this.baseUrl = baseUrl;
        this.secretKey = secretKey;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }
}
