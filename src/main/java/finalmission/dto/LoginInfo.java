package finalmission.dto;

import finalmission.domain.Member;

public record LoginInfo(
        Long id,
        String name,
        String email
) {
    public static LoginInfo from(final Member member) {
        return new LoginInfo(member.getId(), member.getName(), member.getEmail());
    }
}
