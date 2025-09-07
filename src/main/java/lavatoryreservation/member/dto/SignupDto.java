package lavatoryreservation.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lavatoryreservation.lavatory.domain.Sex;

@Schema(description = "회원 가입 요청 DTO")
public record SignupDto(
        @Schema(description = "회원 이름", example = "홍길동", required = true)
        String name,
        
        @Schema(description = "이메일 주소", example = "hong@example.com", required = true)
        String email,
        
        @Schema(description = "성별", example = "MALE", required = true)
        Sex sex
) {
}
