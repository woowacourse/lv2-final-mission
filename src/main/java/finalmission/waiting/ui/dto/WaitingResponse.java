package finalmission.waiting.ui.dto;

import finalmission.reservation.ui.dto.ReservationTimeResponse;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import finalmission.waiting.domain.Waiting;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record WaitingResponse(
        Long id,
        LocalDate date,
        ReservationTimeResponse time,
        RestaurantResponse restaurant,
        Long memberId,
        String memberNickname
) {

    public static WaitingResponse from(final Waiting waiting) {
        return WaitingResponse.builder()
                .id(waiting.getId())
                .date(waiting.getReservationSlot().getDate())
                .time(ReservationTimeResponse.from(waiting.getReservationSlot().getTime()))
                .restaurant(RestaurantResponse.from(waiting.getReservationSlot().getRestaurant()))
                .memberId(waiting.getMember().getId())
                .memberNickname(waiting.getMember().getNickname())
                .build();
    }
}
