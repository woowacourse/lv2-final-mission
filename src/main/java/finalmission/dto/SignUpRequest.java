package finalmission.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "올바르지 않는 이메일 형식입니다")
        String email,
        @NotBlank(message = "패스워드를 입력해주세요")
        String password
) {
}
