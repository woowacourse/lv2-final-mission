package finalmission.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TimeAddRequest(
        String username,
        List<LocalDateTime> dateTimes
) {
}
