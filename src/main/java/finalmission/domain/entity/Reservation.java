package finalmission.domain.entity;

import jakarta.persistence.Column;
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
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_time_id")
    private LessonTime lessonTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    protected Reservation() {
    }

    public Reservation(
            Long id,
            ReservationStatus status,
            LocalDate date,
            Member member,
            LessonTime lessonTime,
            Trainer trainer
    ) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.member = member;
        this.lessonTime = lessonTime;
        this.trainer = trainer;
    }

    public static Reservation createWithoutId(
            ReservationStatus status,
            LocalDate date,
            Member member,
            LessonTime lessonTime,
            Trainer trainer
    ) {
        return new Reservation(null, status, date, member, lessonTime, trainer);
    }

    public boolean isReserved() {
        return this.status == ReservationStatus.RESERVED;
    }

    public boolean availRefund(LocalDate comparedDate) {
        return this.date.minusDays(1).isAfter(comparedDate);
    }

    public LocalTime reservedTime() {
        return this.lessonTime.getTime();
    }

    public Long getId() {
        return id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Member getMember() {
        return member;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public LocalDate getDate() {
        return date;
    }
}
