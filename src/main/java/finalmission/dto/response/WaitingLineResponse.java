package finalmission.dto.response;

import finalmission.domain.WaitingLine;
import java.util.List;

public record WaitingLineResponse(
        Long id,
        List<WaitingMemberResponse> waiting
) {
    public static WaitingLineResponse from(WaitingLine waitingLine) {
        return new WaitingLineResponse(
                waitingLine.getId(),
                waitingLine.getWaitingMembers().stream()
                        .map(WaitingMemberResponse::from)
                        .toList()
        );
    }
}
