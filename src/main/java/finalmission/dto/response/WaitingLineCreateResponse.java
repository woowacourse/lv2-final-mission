package finalmission.dto.response;

import finalmission.domain.Member;
import finalmission.domain.WaitingLine;
import java.util.List;

public record WaitingLineCreateResponse(
        Long id,
        List<Member> waiting
) {

    public static WaitingLineCreateResponse from(WaitingLine waitingLine) {
        return new WaitingLineCreateResponse(waitingLine.getId(), waitingLine.getWaiting());
    }
}
