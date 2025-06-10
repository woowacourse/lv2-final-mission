package finalmission.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "holiday")
public record HolidayProperties(
        String baseUrl,
        String secretKey
) {
}
