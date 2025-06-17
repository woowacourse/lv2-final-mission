package ordering.dto.request;

import java.util.List;

public record TwilioMailSend(
    List<Personalization> personalizations,
    From from,
    List<Content> content) {

    public static TwilioMailSend from(String toEmail, String subject, String fromEmail,
        String contentValue) {
        return new TwilioMailSend(
            List.of(new Personalization(List.of(new To(toEmail)), subject)),
            new From(fromEmail),
            List.of(new Content("text/plain", contentValue))
        );
    }

    private record Personalization(
        List<To> to,
        String subject) {

    }

    private record To(
        String email) {

    }

    private record From(
        String email) {

    }

    private record Content(
        String type,
        String value) {

    }
}
