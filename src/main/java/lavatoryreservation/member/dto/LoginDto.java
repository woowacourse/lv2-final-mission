package lavatoryreservation.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record LoginDto(
        @Schema(description = "이메일 주소", example = "hong@example.com", required = true)
        String email
) {
}
