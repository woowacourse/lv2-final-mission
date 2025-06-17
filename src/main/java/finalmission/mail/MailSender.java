package finalmission.mail;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private final Resend resend;
    private final String from;

    public MailSender(
            @Value("${resend.apikey}") final String apiKey,
            @Value("${mail.from}") final String from
    ) {
        this.resend = new Resend(apiKey);
        this.from = from;
    }

    public EmailResult send(final String to, final String subject, final String html) {
        final CreateEmailOptions opts = CreateEmailOptions.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .html(html)
                .build();

        try {
            final CreateEmailResponse result = resend.emails().send(opts);
            return new EmailResult(result.getId());
        } catch (final ResendException e) {
            throw new IllegalStateException("메일 전송 실패", e);
        }
    }
}
