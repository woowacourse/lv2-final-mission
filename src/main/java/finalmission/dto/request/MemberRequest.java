package finalmission.dto.request;

import finalmission.domain.Member;

public record MemberRequest(
        String email,
        String name,
        String password
) {
    public Member toMember() {
        return new Member(email, name, password);
    }
}
