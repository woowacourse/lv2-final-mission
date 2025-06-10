package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.member.Member;
import finalmission.ballparkreservation.schedule.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public Reservation(final Member member, final Schedule schedule) {
        this.member = member;
        this.schedule = schedule;
    }

    public int getAmount(final boolean isHoliday) {
        int amount = schedule.getWeekdayAmount();
        if (isHoliday || schedule.isWeekend()) {
            amount = schedule.getHolidayAmount();
        }

        if (member.isDiscountApply()) {
            return (int) (amount * 0.7);
        }
        return amount;
    }
}
