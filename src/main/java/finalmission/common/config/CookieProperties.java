package finalmission.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cookie")
public class CookieProperties {

    private Long maxAge;

    public CookieProperties(final Long maxAge) {
        this.maxAge = maxAge;
    }

    public Long getMaxAge() {
        return maxAge;
    }
}
