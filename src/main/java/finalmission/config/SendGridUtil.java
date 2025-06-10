package finalmission.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import finalmission.reservation.dto.ReservationResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendGridUtil {

    private final SendGrid sendGrid;
    private final String fromEmail;

    public SendGridUtil(SendGrid sendGrid, @Value("${spring.sendgrid.from}") String fromEmail) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
    }

    public void sendEmail(String memberEmail, ReservationResponse response) throws IOException {
        Email from = new Email(fromEmail);
        String subject = "회의실 예약 완료";
        Email to = new Email(memberEmail);
        Content content = new Content("text/plain", response.room().name() + " 회의실 예약이 완료되었습니다.");

        Mail mail = new Mail(from, subject, to, content);
        send(mail);
    }

    private void send(Mail mail) throws IOException {
        sendGrid.addRequestHeader("X-Mock", "true");

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);
    }
}
