package finalmission.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mail")
public record MailProperties(
        String apiKey,
        String from
) {
}
