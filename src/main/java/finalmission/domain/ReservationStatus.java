package finalmission.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    CONFIRMED("확정"),
    CANCELED("취소");

    private final String title;

    ReservationStatus(final String title) {
        this.title = title;
    }
}
