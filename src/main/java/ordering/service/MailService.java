package ordering.service;

import ordering.config.client.TwilioMailRestClient;
import ordering.dto.request.TwilioMailSend;
import ordering.dto.response.OrderResponse;
import ordering.entity.EmailStatus;
import ordering.entity.Order;
import ordering.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final OrderJpaRepository orderJpaRepository;
    private final TwilioMailRestClient twilioMailRestClient;

    @Value("${security.twilio.to.email}")
    private String toEmail;

    @Value("${security.twilio.from.email}")
    private String fromEmail;

    public MailService(OrderJpaRepository orderJpaRepository,
        TwilioMailRestClient twilioMailRestClient) {
        this.orderJpaRepository = orderJpaRepository;
        this.twilioMailRestClient = twilioMailRestClient;
    }

    public void sendMailWithProcessingOrder(OrderResponse orderResponse) {
        String content = orderResponse.toText();

        TwilioMailSend request = TwilioMailSend.from(
            toEmail, "발주 내역이 도착했습니다.", fromEmail, content);
        String result = twilioMailRestClient.requestConfirmation(request);

        validateAcceptedMail(result);
        changeEmailStatus(orderResponse.id(), EmailStatus.DONE);
    }

    public void sendMailWithDeletedOrder(OrderResponse orderResponse) {
        String content = orderResponse.toText();

        TwilioMailSend request = TwilioMailSend.from(
            toEmail, "발주 취소 내역이 도착했습니다.", fromEmail, content);
        String result = twilioMailRestClient.requestConfirmation(request);

        validateAcceptedMail(result);
        changeEmailStatus(orderResponse.id(), EmailStatus.DONE);
    }

    private void validateAcceptedMail(String result) {
        if (!result.equals("Accepted")) {
            throw new IllegalStateException();
        }
    }

    private void changeEmailStatus(Long orderId, EmailStatus emailStatus) {
        Order order = orderJpaRepository.findById(orderId)
            .orElseThrow(IllegalArgumentException::new);
        order.setEmailStatus(emailStatus);
    }
}
