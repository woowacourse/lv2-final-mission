package finalmission.reservation;

import finalmission.reservation.domain.dto.ReservationRequestDto;
import finalmission.room.domain.Room;
import finalmission.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User user;

    protected Reservation() {
    }

    public Reservation(String content, Room room, User user) {
        this.content = content;
        this.room = room;
        this.user = user;
    }

    public void update(ReservationRequestDto requestDto) {
        this.content = requestDto.content();
    }
}
