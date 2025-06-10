package finalmission.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nickname")
public class RandomNicknameProperties {

    private String apiKey;
    private String baseUrl;
    private String randomUrl;

    public RandomNicknameProperties(final String apiKey, final String baseUrl, final String randomUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.randomUrl = randomUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getRandomUrl() {
        return randomUrl;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setRandomUrl(final String randomUrl) {
        this.randomUrl = randomUrl;
    }
}
