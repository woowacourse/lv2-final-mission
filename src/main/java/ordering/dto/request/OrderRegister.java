package ordering.dto.request;

public record OrderRegister(
    String username,
    Long categoryId,
    Long productId,
    Long count,
    String detail) {

}
