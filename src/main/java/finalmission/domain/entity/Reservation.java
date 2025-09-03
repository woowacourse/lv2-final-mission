package finalmission.domain.entity;

import finalmission.dto.ReservationUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

    private LocalDate date;

    private LocalTime time;

    public Reservation(final Long id, final Member member, final Manager manager, final Tour tour, final LocalDate date,
                       final LocalTime time) {
        this.id = id;
        this.member = member;
        this.manager = manager;
        this.tour = tour;
        this.date = date;
        this.time = time;
    }

    public Reservation(final Member member, final Manager manager, final Tour tour, final LocalDate date,
                       final LocalTime time) {
        this(null, member, manager, tour, date, time);
    }

    public Reservation updateReservation(ReservationUpdateRequest request) {
        // TODO: 수정사항 선택하기
        return this;
    }
}
