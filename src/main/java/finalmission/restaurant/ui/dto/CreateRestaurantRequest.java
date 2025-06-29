package finalmission.restaurant.ui.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRestaurantRequest(
        @NotNull(message = "음식점 이름은 필수입니다.")
        String name,
        @NotNull(message = "음식점 설명은 필수입니다.")
        String description,
        @NotNull(message = "음식점 위치는 필수입니다.")
        String place,
        @NotNull(message = "음식점 전화번호는 필수입니다.")
        String phoneNumber,
        @NotNull(message = "최대 예약 수는 필수입니다.")
        int maxReservationCount
) {

}
