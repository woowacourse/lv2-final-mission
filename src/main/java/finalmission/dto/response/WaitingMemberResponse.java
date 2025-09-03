package finalmission.dto.response;

import finalmission.domain.WaitingMember;
import java.time.LocalDateTime;

public record WaitingMemberResponse(
        Long id,
        MemberResponse member,
        LocalDateTime createdAt
) {
    public static WaitingMemberResponse from(WaitingMember waitingMember) {
        return new WaitingMemberResponse(
                waitingMember.getId(),
                MemberResponse.from(waitingMember.getMember()),
                waitingMember.getCreatedAt()
        );
    }
} 