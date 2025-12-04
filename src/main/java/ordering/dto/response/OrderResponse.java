package ordering.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import ordering.entity.Order;

public record OrderResponse(
    Long id,
    String username,
    String category,
    String product,
    Long count,
    String emailStatus,
    String orderStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getUser().getName(),
            order.getCategory().getName(),
            order.getProduct().getName(),
            order.getCount(),
            order.getEmailStatus().getText(),
            order.getOrderStatus().getText(),
            order.getCreatedAt()
        );
    }

    public String toText() {
        return "id: " + id
            + "username: " + username
            + "category: " + category
            + "product: " + product
            + "count: " + count;
    }
}
