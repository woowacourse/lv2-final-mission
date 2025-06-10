package finalmission.dto.response;

import finalmission.domain.VoteStatics;

import java.util.List;

public record VoteStaticsResponses(
        List<VoteStatics> statics
) {
    public static VoteStaticsResponses from(List<VoteStatics> statics) {
        return new VoteStaticsResponses(statics);
    }
}
