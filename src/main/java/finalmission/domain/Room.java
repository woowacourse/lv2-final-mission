package finalmission.domain;

import finalmission.exception.RoomException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private Long seatsCount;

    public Room() {
    }

    public Room(Long id, String roomName, Long seatsCount) {
        validate(roomName, seatsCount);
        this.id = id;
        this.roomName = roomName;
        this.seatsCount = seatsCount;
    }

    private void validate(String roomName, Long seatsCount) {
        validateRoomName(roomName);
        validateSeatsCount(seatsCount);
    }

    private void validateSeatsCount(Long seatsCount) {
        if (seatsCount == null) {
            throw new RoomException("의자 개수 없이는 방을 생성할 수 없습니다.");
        }
    }

    private void validateRoomName(String roomName) {
        if (roomName.isBlank()) {
            throw new RoomException("방 이름 없이 방을 생성할 수 없습니다.");
        }
    }
}
