package finalmission.waiting.ui.dto;

import finalmission.reservation.ui.dto.ReservationTimeResponse;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import finalmission.waiting.domain.Waiting;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record WaitingResponse(
        Long id,
        LocalDate date,
        ReservationTimeResponse time,
        RestaurantResponse restaurant,
        Long memberId,
        String memberNickname,
        LocalDateTime createdAt
) {

    public static WaitingResponse from(final Waiting waiting) {
        return new WaitingResponse(
                waiting.getId(),
                waiting.getReservationSlot().getDate(),
                ReservationTimeResponse.from(waiting.getReservationSlot().getTime()),
                RestaurantResponse.from(waiting.getReservationSlot().getRestaurant()),
                waiting.getMember().getId(),
                waiting.getMember().getNickname(),
                waiting.getCreatedAt()
        );
    }
}
