package ordering.dto.request;

import java.util.List;

public record TwilioMailSend(
    List<Personalization> personalizations,
    From from,
    List<Content> content) {

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
