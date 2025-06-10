package finalmission.domain;

import java.time.LocalDateTime;
import java.util.List;

public record TimeStatics(
        LocalDateTime dateTime,
        List<String> voters
) {
}
