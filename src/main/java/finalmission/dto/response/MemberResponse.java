package finalmission.dto.response;

import finalmission.domain.Member;

public record MemberResponse(
        Long id,
        String name,
        String email
) {
    public static MemberResponse from(final Member savedMember) {
        return new MemberResponse(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getEmail()
        );
    }
}
