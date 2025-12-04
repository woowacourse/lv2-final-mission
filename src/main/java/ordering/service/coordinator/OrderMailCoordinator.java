package ordering.service.coordinator;

import ordering.dto.request.OrderRegister;
import ordering.dto.response.OrderResponse;
import ordering.service.MailService;
import ordering.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderMailCoordinator {

    private final OrderService orderService;
    private final MailService mailService;

    public OrderMailCoordinator(OrderService orderService, MailService mailService) {
        this.orderService = orderService;
        this.mailService = mailService;
    }

    public OrderResponse sendOrder(OrderRegister request) {
        OrderResponse response = orderService.registerOrder(request);
        mailService.sendMailWithProcessingOrder(response);

        return response;
    }

    public void deleteAndSendOrder(Long orderId) {
        OrderResponse response = orderService.deleteOrder(orderId);
        mailService.sendMailWithDeletedOrder(response);
    }
}
