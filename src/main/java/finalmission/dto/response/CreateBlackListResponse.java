package finalmission.dto.response;

import finalmission.entity.BlackList;
import finalmission.entity.Member;
import java.time.LocalDateTime;

public record CreateBlackListResponse(Long id, Long member_id, String name, String reason, LocalDateTime bannedSince) {

    public static CreateBlackListResponse from(final BlackList saved) {
        Member member = saved.getMember();
        return new CreateBlackListResponse(saved.getId(), member.getId(), member.getName(),
                saved.getReason(), saved.getBannedSince());
    }
}
