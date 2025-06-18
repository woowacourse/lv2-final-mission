package finalmission.auth.user;

import finalmission.member.domain.Role;

public record MemberInfo(Long id, String name, String email, Role role) {

    public boolean isCoach() {
        return role.equals(Role.COACH);
    }
}
