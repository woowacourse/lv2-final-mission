package finalmission.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.") String email,

        @NotBlank(message = "패스 워드는 빈 값일 수 없습니다.")
        String password,

        @NotBlank(message = "이름은 빈 값일 수 없습니다.")
        String name) {
}
