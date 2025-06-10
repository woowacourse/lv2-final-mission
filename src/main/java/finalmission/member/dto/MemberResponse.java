package finalmission.member.dto;

public record MemberResponse(
        Long id,
        String name,
        String email
) {
    public static MemberResponse toResponse(MemberResult result) {
        return new MemberResponse(result.id(), result.name(), result.email());
    }
}
