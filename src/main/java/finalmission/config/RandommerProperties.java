package finalmission.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "randommer")
public record RandommerProperties(String baseUrl, String apiKey, int connectTimeout, int readTimeout) {
}
