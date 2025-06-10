package finalmission.dto;

import finalmission.domain.Member;

public record LoginMemberInfo(long id, String name, String email) {

    public LoginMemberInfo(Member member) {
        this(member.id(), member.name(), member.email());
    }
}
