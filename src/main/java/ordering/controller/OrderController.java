package ordering.controller;

import java.util.List;
import ordering.dto.request.OrderRegister;
import ordering.dto.response.OrderResponse;
import ordering.service.OrderService;
import ordering.service.coordinator.OrderMailCoordinator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderMailCoordinator orderMailCoordinator;
    private final OrderService orderService;

    public OrderController(OrderMailCoordinator orderMailCoordinator, OrderService orderService) {
        this.orderMailCoordinator = orderMailCoordinator;
        this.orderService = orderService;
    }

    @GetMapping("/{userId}")
    public List<OrderResponse> readOrdersByUser(@PathVariable Long userId) {
        return orderService.findByUser(userId);
    }

    @PostMapping
    public OrderResponse createOrder(OrderRegister request) {
        return orderMailCoordinator.sendOrder(request);
    }

    @PutMapping
    public OrderResponse modifyOrder(OrderRegister request) {
        return orderMailCoordinator.sendOrder(request);
    }
}
