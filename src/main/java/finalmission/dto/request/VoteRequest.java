package finalmission.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record VoteRequest(
        String name,
        List<LocalDateTime> values
) {
}
