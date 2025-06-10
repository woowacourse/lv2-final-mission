package finalmission.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TimeResponses(
        String username,
        List<TimeResponse> values
) {
    public static TimeResponses from(String username, List<LocalDateTime> dateTimes) {
        List<TimeResponse> timeResponses = dateTimes.stream()
                .map(dateTime -> new TimeResponse(dateTime.toLocalDate(), dateTime.toLocalTime()))
                .toList();
        return new TimeResponses(username, timeResponses);
    }
}
