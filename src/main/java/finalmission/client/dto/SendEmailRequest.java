package finalmission.client.dto;

public record SendEmailRequest(String to,
                               String subject,
                               String content) {
}
