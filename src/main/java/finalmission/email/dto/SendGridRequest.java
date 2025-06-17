package finalmission.email.dto;

import java.util.List;

public record SendGridRequest(
        List<Personalization> personalizations,
        Email from,
        List<Content> content
) {
}
