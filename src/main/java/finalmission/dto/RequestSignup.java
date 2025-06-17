package finalmission.dto;

public record RequestSignup(
        String email,
        String password,
        String name,
        Integer age
) {
}
