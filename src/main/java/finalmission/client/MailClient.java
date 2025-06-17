package finalmission.client;

import finalmission.client.dto.SendEmailRequest;

public interface MailClient {
    void sendEmail(SendEmailRequest request);
}
