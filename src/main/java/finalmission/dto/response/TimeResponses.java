package finalmission.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TimeResponses(
        String name,
        List<LocalDateTime> values
) {
    public static TimeResponses from(String name, List<LocalDateTime> dateTimes) {
        return new TimeResponses(name, dateTimes);
    }
}
