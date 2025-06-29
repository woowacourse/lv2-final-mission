package finalmission.email.infrastructure.twilio.dto;

import java.util.List;

public record Personalization(
        List<EmailTo> to,
        String subject
) {

}
