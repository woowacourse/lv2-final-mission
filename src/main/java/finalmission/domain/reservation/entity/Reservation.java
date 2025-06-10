package finalmission.domain.reservation.entity;

import finalmission.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String lesson;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    public Reservation(final Member member, final String lesson, final LocalDate date, final LocalTime time) {
        this.member = member;
        this.lesson = lesson;
        this.date = date;
        this.time = time;
    }

    public void update(final LocalDate date, final LocalTime time) {
        this.date = date;
        this.time = time;
    }
}
