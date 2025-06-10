package finalmission.reservation.application.dto;

import jakarta.validation.constraints.NotNull;

public record RoomReservationRequest(@NotNull(message = "회의실은 필수 입력값입니다.") Long roomId) {
}
