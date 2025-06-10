package finalmission.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record VoteRequest(
        List<LocalDateTime> values
) {
}
