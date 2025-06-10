package finalmission.member.infrastructure;

import finalmission.global.config.ApiProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "external.name")
public class NameApiProperties extends ApiProperties {
    private final String secretKey;

    public NameApiProperties(String baseUrl, int connectTimeout, int readTimeout, String secretKey) {
        super(baseUrl, connectTimeout, readTimeout);
        this.secretKey = secretKey;
    }
}
