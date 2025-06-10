package woowaTable.user.application.dto;

import woowaTable.user.domain.Role;

public record LoginCheckRequest(
        Long id,
        Role role
) {
    public static LoginCheckRequest of(final Long id, final Role role) {
    }
}
