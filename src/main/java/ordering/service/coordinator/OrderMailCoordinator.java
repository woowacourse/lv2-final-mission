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
        String result = mailService.sendMail();

        if(result.equals("Accepted")) {
            throw new IllegalStateException();
        }

        return response;
    }
}
