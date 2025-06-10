package finalmission.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record VoteResponses(
        String name,
        List<LocalDateTime> values
) {
    public static VoteResponses from(String name, List<LocalDateTime> dateTimes) {
        return new VoteResponses(name, dateTimes);
    }
}
