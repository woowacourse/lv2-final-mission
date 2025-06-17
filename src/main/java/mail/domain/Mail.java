package mail.domain;

import lombok.Getter;

@Getter
public class Mail {

    private final String fromEmail;
    private final String subject;
    private final String toEmail;
    private final String content;


    public Mail(String fromEmail, String subject, String toEmail, String content) {
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.toEmail = toEmail;
        this.content = content;
    }
}
