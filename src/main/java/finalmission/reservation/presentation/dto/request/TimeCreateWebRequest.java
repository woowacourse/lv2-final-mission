package finalmission.reservation.presentation.dto.request;

import java.time.LocalTime;

public record TimeCreateWebRequest(LocalTime startAt) {

    public TimeCreateWebRequest {
        validateStartAt(startAt);
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간은 null이 될 수 없습니다.");
        }
    }
}
