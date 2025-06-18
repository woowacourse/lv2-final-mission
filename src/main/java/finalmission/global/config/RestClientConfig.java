package finalmission.global.config;

import finalmission.infrastructure.sms.CoolSmsAuthorizationInterceptor;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final CoolSmsAuthorizationInterceptor coolSmsAuthorizationInterceptor;

    @Value("${mail.mailgun.secret-key}")
    private String secretKey;

    @Bean
    public RestClient mailRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.mailgun.net/v3/sandbox16abdadf77a046d1b55994b80db6e99a.mailgun.org")
                .defaultHeader("Authorization",
                        "Basic " + Base64.getEncoder().encodeToString(("api:" + secretKey).getBytes()))
                .build();
    }

    @Bean
    public RestClient smsRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.solapi.com/messages/v4/send-many")
                .requestInterceptor(coolSmsAuthorizationInterceptor)
                .build();
    }
}
