package finalmission.dto.request;

import finalmission.domain.MemberRole;

public record MemberInfo(Long id, String name, MemberRole role) {
}
