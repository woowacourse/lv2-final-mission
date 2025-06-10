package ordering.service;

import java.time.LocalDateTime;
import ordering.dto.request.OrderRegister;
import ordering.dto.response.OrderResponse;
import ordering.entity.Category;
import ordering.entity.EmailStatus;
import ordering.entity.Order;
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

    public OrderResponse registerOrder(OrderRegister request) {
        return OrderResponse.from(orderJpaRepository.save(createOrder(request)));
    }

    private Order createOrder(OrderRegister request) {
        User user = userJpaRepository.findByName(request.username())
            .orElseThrow(IllegalArgumentException::new);
        Category category = categoryJpaRepository.findById(request.categoryId())
            .orElseThrow(IllegalArgumentException::new);
        Product product = productJpaRepository.findById(request.productId())
            .orElseThrow(IllegalArgumentException::new);

        return new Order(user, category, product, request.count(), request.detail(),
            LocalDateTime.now(), EmailStatus.DONE);
    }
}
