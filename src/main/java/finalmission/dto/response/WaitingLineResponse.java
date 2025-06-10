package finalmission.dto.response;

import finalmission.domain.WaitingLine;
import finalmission.domain.WaitingMember;
import java.util.List;

public record WaitingLineResponse(
        Long id,
        List<WaitingMember> waiting
) {

    public static WaitingLineResponse from(WaitingLine waitingLine) {
        return new WaitingLineResponse(waitingLine.getId(), waitingLine.getWaitingMembers());
    }
}
