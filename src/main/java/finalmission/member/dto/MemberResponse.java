package finalmission.member.dto;

import finalmission.member.domain.Member;
import finalmission.member.domain.Role;

public record MemberResponse(long id,
                             String email,
                             Role role) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );
    }
}
