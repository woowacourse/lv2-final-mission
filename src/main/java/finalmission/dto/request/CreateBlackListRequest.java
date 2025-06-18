package finalmission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateBlackListRequest(
        @Positive(message = "유효하지 않은 멤버 정보입니다.") Long memberId,
        @NotBlank(message = "밴 사유가 비어있습니다.") String reason
) {
}
