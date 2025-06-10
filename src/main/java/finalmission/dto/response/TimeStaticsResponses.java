package finalmission.dto.response;

import finalmission.domain.TimeStatics;

import java.util.List;

public record TimeStaticsResponses(
        List<TimeStatics> statics
) {
    public static TimeStaticsResponses from(List<TimeStatics> statics) {
        return new TimeStaticsResponses(statics);
    }
}
