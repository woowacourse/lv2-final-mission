package finalmission.reservation.entity;

import finalmission.member.entity.Member;
import finalmission.room.entity.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String purpose;

    public Reservation(Member member, Room room, LocalDate date, LocalTime startTime, LocalTime endTime,
                       String purpose) {
        this(null, member, room, date, startTime, endTime, purpose);
    }

    public boolean isMine(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public void update(String purpose) {
        this.purpose = purpose;
    }
}
