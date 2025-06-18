package finalmission.member.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
   @NotBlank String email,
   @NotBlank String password,
   @NotBlank String name
) {
}
