package finalmission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateMeetingRoomRequest(
        @NotBlank(message = "이름은 빈 값일 수 없습니다.") String name,
        @NotBlank(message = "설명은 빈 값일 수 없습니다.") String describe,
        @Positive(message = "가용 인원은 0보다 커야 합니다.") Integer availablePeopleCount
) {
}
