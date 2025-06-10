package finalmission.reservation.domain;

import finalmission.exercisecourse.domain.ExerciseCourse;
import finalmission.member.domain.Member;
import finalmission.reservationtime.domain.ReservationTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseCourse exerciseCourse;

    public Reservation(Long id, String name, LocalDate date, Member member, ReservationTime time, ExerciseCourse exerciseCourse) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.member = member;
        this.time = time;
        this.exerciseCourse = exerciseCourse;
    }

    public Reservation() {
    }

    public static Reservation createWithoutId(String name, LocalDate date, Member member, ReservationTime time, ExerciseCourse exerciseCourse) {
        return new Reservation(null, name, date, member, time, exerciseCourse);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Member getMember() {
        return member;
    }

    public ReservationTime getTime() {
        return time;
    }

    public ExerciseCourse getExerciseCourse() {
        return exerciseCourse;
    }
}
