package shh.reservation.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shh.alias.domain.Alias;
import shh.member.domain.Member;
import shh.stall.domain.Stall;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stall stall;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Embedded
    private Alias alias;

    public Reservation(LocalDate date, ReservationTime time, Stall stall, Member member, Alias alias) {
        this(null, date, time, stall, member, alias);
    }

    public void updateDateAndTime(final LocalDate date, final ReservationTime time) {
        this.date = date;
        this.time = time;
    }
}
