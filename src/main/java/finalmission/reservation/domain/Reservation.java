package finalmission.reservation.domain;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import finalmission.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    @ManyToOne(optional = false, cascade = {PERSIST, DETACH, REFRESH, MERGE})
    @JoinColumn(name = "coach_id")
    private Member coach;

    @ManyToOne(optional = false, cascade = {PERSIST, DETACH, REFRESH, MERGE})
    @JoinColumn(name = "crew_id")
    private Member crew;

    private Reservation(LocalDate date,
                        LocalTime time,
                        ReservationState state,
                        Member coach,
                        Member crew) {
        this.date = date;
        this.time = time;
        this.state = state;
        this.coach = coach;
        this.crew = crew;
    }

    public static Reservation from(LocalDate date,
                                   LocalTime time,
                                   Member coach,
                                   Member crew) {
        validate(LocalDateTime.of(date, time));
        return new Reservation(date, time, ReservationState.WAITING, coach, crew);
    }

    private static void validate(LocalDateTime dateTime) {
        if (LocalDateTime.now().isAfter(dateTime)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 예약 시간입니다.");
        }
    }

    public void cancelByCrew() {
        validateStateAlreadyApproval();
        if (this.state == ReservationState.CANCEL) {
            throw new IllegalArgumentException("[ERROR] 이미 취소된 예약입니다. : " + id);
        }
        this.state = ReservationState.CANCEL;
    }

    public void approval() {
        validateStateAlreadyApproval();
        this.state = ReservationState.APPROVAL;
    }


    public void waiting() {
        this.state = ReservationState.WAITING;
    }

    public void updateByCrew(LocalDate date, LocalTime time, Member coach) {
        validateStateAlreadyApproval();
        this.date = date;
        this.time = time;
        this.coach = coach;
    }

    public void update(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public void cancel() {
        if (this.state == ReservationState.CANCEL) {
            throw new IllegalArgumentException("[ERROR] 이미 취소된 예약입니다. : " + id);
        }
        this.state = ReservationState.CANCEL;
    }

    private void validateStateAlreadyApproval() {
        if (this.state == ReservationState.APPROVAL) {
            throw new IllegalArgumentException("[ERROR] 이미 승인된 예약입니다. : " + id);
        }
    }
}
