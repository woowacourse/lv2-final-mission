package finalmission.member.dto;

import finalmission.member.domain.Role;

public record LoginRequest(String email, String password) {
}
