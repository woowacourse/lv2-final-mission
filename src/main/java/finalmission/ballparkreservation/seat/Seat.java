package finalmission.ballparkreservation.seat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    @Enumerated
    private SeatRank rank;

    public Seat(final int number, final SeatRank rank) {
        this.number = number;
        this.rank = rank;
    }

    public int getWeekdayAmount() {
        return rank.getWeekdayPrice();
    }

    public int getHolidayAmount() {
        return rank.getHolidayPrice();
    }
}
