package finalmission.dto.response;

import finalmission.domain.Member;

public record MemberCreateResponse(
        Long id,
        String email,
        String name
) {

    public static MemberCreateResponse from(Member master) {
        return new MemberCreateResponse(master.getId(), master.getEmail(), master.getName());
    }
}
