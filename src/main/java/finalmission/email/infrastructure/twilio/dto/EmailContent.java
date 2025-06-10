package finalmission.email.infrastructure.twilio.dto;

public record EmailContent(
        String type,
        String value
) {

    public static EmailContent plainText(final String value) {
        return new EmailContent("text/plain", value);
    }
}
