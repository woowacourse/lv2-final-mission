package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Getter
@NoArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reservedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Reservation(Long id, LocalDate reservedAt, Lecture lecture, Member member) {
        this.id = id;
        this.reservedAt = reservedAt;
        this.lecture = lecture;
        this.member = member;
    }

    public Reservation(LocalDate reservedAt, Lecture lecture, Member member) {
        this(null, reservedAt, lecture, member);
    }
}

