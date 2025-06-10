package finalmission.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationSlot {

    @Column(nullable = false)
    private LocalDate date;

    @JoinColumn(name = "time_id", nullable = false)
    @ManyToOne
    private ReservationTime time;

    @Column(nullable = false)
    private String place;

    public ReservationSlot(
            final LocalDate date,
            final ReservationTime time,
            final String place
    ) {
        validateDate(date);
        validateTime(time);
        validatePlace(place);

        this.date = date;
        this.time = time;
        this.place = place;
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 null이면 안됩니다.");
        }
    }

    private void validateTime(final ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 null이면 안됩니다.");
        }
    }

    private void validatePlace(final String place) {
        if (place == null || place.isBlank()) {
            throw new IllegalArgumentException("장소는 null 이거나 빈 문자열일 수 없습니다.");
        }
    }
}
