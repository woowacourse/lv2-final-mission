package finalmission.domain.reservation;

import finalmission.domain.member.Coach;
import finalmission.domain.member.Crew;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_time")
    private ReservationTime reservationTime;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    protected Reservation() {
    }

    public Reservation(Long id, Coach coach, Crew crew, ReservationTime reservationTime,
        LocalDate date, ReservationStatus reservationStatus
    ) {
        this.id = id;
        this.coach = coach;
        this.crew = crew;
        this.reservationTime = reservationTime;
        this.date = date;
        this.reservationStatus = reservationStatus;
    }

    public Reservation(Coach coach, Crew crew, ReservationTime reservationTime, LocalDate date,
        ReservationStatus reservationStatus) {
        this.coach = coach;
        this.crew = crew;
        this.reservationTime = reservationTime;
        this.date = date;
        this.reservationStatus = reservationStatus;
    }

    public boolean isOwnerCrewRequest(Long crewId) {
        return this.crew.getId().equals(crewId);
    }

    public boolean isOwnerCoachRequest(Long coachId) {
        return this.coach.getId().equals(coachId);
    }

    public void changeStatus(ReservationStatus status) {
        this.reservationStatus = status;
    }

    public Long getId() {
        return id;
    }

    public Coach getCoach() {
        return coach;
    }

    public Crew getCrew() {
        return crew;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
}
