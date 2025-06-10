package ordering.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ordering.dto.request.OrderRegister;
import ordering.dto.response.OrderResponse;
import ordering.repository.CategoryJpaRepository;
import ordering.repository.OrderJpaRepository;
import ordering.repository.ProductJpaRepository;
import ordering.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderServiceTest {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private OrderService orderService;

    @BeforeEach
    void init() {
        orderService = new OrderService(
            orderJpaRepository, userJpaRepository, categoryJpaRepository, productJpaRepository
        );
    }

    @Test
    @DisplayName("사용자 입력에 대한 발주를 저장할 수 있다.")
    void createOrder() {
        OrderRegister request = new OrderRegister(
            "사용자1", 1L, 1L, 3L, "사무용품으로 볼펜 구입");

        OrderResponse response = orderService.registerOrder(request);

        assertAll(() -> {
            assertThat(orderJpaRepository.findById(response.id()).isPresent()).isTrue();
            assertThat(orderJpaRepository.findAll()).hasSize(1);
        });
    }
}
