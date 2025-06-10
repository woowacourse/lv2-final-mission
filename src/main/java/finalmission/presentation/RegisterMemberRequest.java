package finalmission.presentation;

import jakarta.validation.constraints.NotBlank;

public record RegisterMemberRequest(
    @NotBlank
    String name
) {

}
