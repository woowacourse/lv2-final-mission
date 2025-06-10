package finalmission.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record TimeAddRequest(
        String name,
        List<LocalDateTime> values
) {
}
