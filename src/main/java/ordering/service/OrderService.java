package ordering.service;

import java.time.LocalDateTime;
import java.util.List;
import ordering.dto.request.OrderRegister;
import ordering.dto.response.OrderResponse;
import ordering.entity.Category;
import ordering.entity.EmailStatus;
import ordering.entity.Order;
import ordering.entity.OrderStatus;
import ordering.entity.Product;
import ordering.entity.User;
import ordering.repository.CategoryJpaRepository;
import ordering.repository.OrderJpaRepository;
import ordering.repository.ProductJpaRepository;
import ordering.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public OrderService(OrderJpaRepository orderJpaRepository, UserJpaRepository userJpaRepository,
        CategoryJpaRepository categoryJpaRepository, ProductJpaRepository productJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    public List<OrderResponse> findByUser(Long userId) {
        return orderJpaRepository.findByUserId(userId).stream()
            .map(OrderResponse::from)
            .toList();
    }

    public OrderResponse registerOrder(OrderRegister request) {
        return OrderResponse.from(orderJpaRepository.save(createProcessingOrder(request)));
    }

    private Order createProcessingOrder(OrderRegister request) {
        User user = userJpaRepository.findByName(request.username())
            .orElse(new User(request.username())); // 없는 사용자라면 새로 생성
        Category category = categoryJpaRepository.findById(request.categoryId())
            .orElseThrow(IllegalArgumentException::new);
        Product product = productJpaRepository.findById(request.productId())
            .orElseThrow(IllegalArgumentException::new);

        return new Order(user, category, product, request.count(), request.detail(),
            LocalDateTime.now(), EmailStatus.PROCESSING, OrderStatus.PROCESSING);
    }

    public OrderResponse deleteOrder(Long orderId) {
        Order order = orderJpaRepository.findById(orderId)
            .orElseThrow(IllegalArgumentException::new);

        order.setOrderStatus(OrderStatus.DELETE);

        return OrderResponse.from(order);
    }
}
