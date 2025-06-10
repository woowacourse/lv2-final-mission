package finalmission.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TimeResponses(
        String username,
        List<LocalDateTime> values
) {
    public static TimeResponses from(String username, List<LocalDateTime> dateTimes) {
        return new TimeResponses(username, dateTimes);
    }
}
