package finalmission.email.dto;

import java.util.List;

public record Personalization(
        List<Email> to,
        String subject
) {
}
