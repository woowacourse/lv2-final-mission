package ordering.dto.response;

import ordering.entity.Order;

public record OrderResponse(
    Long id,
    String username,
    Long categoryId,
    Long productId,
    Long count,
    String emailStatus) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getUser().getName(),
            order.getCategory().getId(),
            order.getProduct().getId(),
            order.getCount(),
            order.getEmailStatus().getText()
        );
    }
}
