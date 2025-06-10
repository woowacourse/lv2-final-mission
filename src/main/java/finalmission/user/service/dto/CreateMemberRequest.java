package finalmission.user.service.dto;

import finalmission.user.domain.Member;
import finalmission.user.domain.vo.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateMemberRequest(
        @NotEmpty
        String name,
        @Email
        String email,
        @NotEmpty
        String password,
        @NotEmpty
        String role
) {
    public Member toEntity() {
        return new Member(Role.from(role), name, email, password);
    }
}
