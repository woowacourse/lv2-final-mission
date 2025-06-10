package finalmission.domain;

import java.time.LocalDateTime;
import java.util.List;

public record VoteStatics(
        LocalDateTime dateTime,
        List<String> voterNames
) {
}
