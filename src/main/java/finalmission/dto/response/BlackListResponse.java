package finalmission.dto.response;

import finalmission.entity.BlackList;
import finalmission.entity.Member;
import java.time.LocalDateTime;

public record BlackListResponse(Long id, Long memberId, String name, String reason, LocalDateTime blackedSince) {

    public static BlackListResponse from(BlackList blackList) {
        Member member = blackList.getMember();
        return new BlackListResponse(blackList.getId(), member.getId(), member.getName(),
                blackList.getReason(), blackList.getBannedSince());
    }
}
