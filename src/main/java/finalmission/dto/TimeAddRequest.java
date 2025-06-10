package finalmission.dto;

import java.util.List;

public record TimeAddRequest(
        String username,
        List<TimeValues> values
) {
}
