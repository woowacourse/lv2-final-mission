package finalmission.member.dto;

import finalmission.member.domian.Role;

public record LoginMember(Long id, String name, Role role) {
}
