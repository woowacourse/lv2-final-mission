package finalmission.member.dto;

import finalmission.member.domain.Member;

public record RegisterMemberRequest(
        String name,
        String email,
        String password
) {

    public Member toMemberEntity() {
        return Member.createNewMember(name, email, password);
    }
}
