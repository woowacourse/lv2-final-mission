package finalmission.member.dto.request;

import finalmission.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @Email String email,
        @NotBlank String nickname,
        @NotBlank String password
) {
    public Member toMember() {
        return new Member(email, nickname, password);
    }
}
