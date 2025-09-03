package finalmission.dto.response;

import finalmission.domain.WaitingLine;
import finalmission.domain.WaitingMember;
import java.util.List;

public record WaitingLineCreateResponse(
        Long id,
        List<WaitingMember> waiting
) {

    public static WaitingLineCreateResponse from(WaitingLine waitingLine) {
        return new WaitingLineCreateResponse(waitingLine.getId(), waitingLine.getWaitingMembers());
    }
}
