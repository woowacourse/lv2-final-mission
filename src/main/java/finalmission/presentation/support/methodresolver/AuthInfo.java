package finalmission.presentation.support.methodresolver;

import finalmission.domain.Role;

public record AuthInfo(
        Long memberId,
        String name,
        Role role
) {
}
