package finalmission.ballparkreservation.schedule;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    @Enumerated(EnumType.STRING)
    private SeatRank rank;

    private LocalDate date;

    public Schedule(final int number, final SeatRank rank, final LocalDate date) {
        this.number = number;
        this.rank = rank;
        this.date = date;
    }

    public int getWeekdayAmount() {
        return rank.getWeekdayPrice();
    }

    public int getHolidayAmount() {
        return rank.getHolidayPrice();
    }
}
