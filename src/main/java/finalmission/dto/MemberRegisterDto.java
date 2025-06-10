package finalmission.dto;

import finalmission.model.Member;

public record MemberRegisterDto(
        String name,
        String email,
        String password
) {
    public Member toMember() {
        return new Member(name, email, password);
    }
}
