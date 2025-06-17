package finalmission.member.dto;

import finalmission.member.domain.Role;

public record RegisterRequest(String email,
                              String password,
                              Role role) {
}
