package finalmission.member.dto;

import finalmission.member.domian.Role;

public record MemberResponse(String name, Role role) {

    public static MemberResponse from(LoginMember loginMember) {
        return new MemberResponse(loginMember.name(), loginMember.role());
    }
}
