package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.room.domain.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Room room;

    protected Reservation() {
    }

    public Reservation(final LocalDateTime time, final String description, final Member member, final Room room) {
        this.time = truncateToMinutes(time);
        this.description = description;
        this.member = member;
        this.room = room;
    }

    private LocalDateTime truncateToMinutes(final LocalDateTime time) {
        return time.truncatedTo(ChronoUnit.MINUTES);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Member getMember() {
        return member;
    }

    public Room getRoom() {
        return room;
    }
}
