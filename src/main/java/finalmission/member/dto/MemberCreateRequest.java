package finalmission.member.dto;

public record MemberCreateRequest(
        String email,
        String password
) {
}
