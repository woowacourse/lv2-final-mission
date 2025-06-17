package finalmission.client;

public record RequestSendMail(
        Personalization[] personalizations,
        From from,
        Content[] content
) {

    public static RequestSendMail of(String to, String from, String subject, String content) {
        return new RequestSendMail(
                new Personalization[]{new Personalization(to, subject)},
                new From(from),
                new Content[]{new Content("text/plain", content)}
        );
    }

    record Personalization(
            To[] to,
            String subject
    ) {
        public Personalization(String email, String subject) {
            this(new To[]{new To(email)}, subject);
        }

        record To(String email) {}
    }

    record From(
            String email
    ) {}

    record Content(
            String type,
            String value
    ) {}
}
