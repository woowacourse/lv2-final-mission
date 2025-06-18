package finalmission.dto.request;

import finalmission.domain.Member;

public record SignUpRequest(String email, String password) {

    public Member toMember(String name) {
        return new Member(null, name, email, password);
    }
}
