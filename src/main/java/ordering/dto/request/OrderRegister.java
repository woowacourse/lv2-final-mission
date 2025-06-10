package ordering.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record OrderRegister(
    String username,
    Long categoryId,
    Long productId,
    Long count,
    String detail,
    Double amount) {

}
