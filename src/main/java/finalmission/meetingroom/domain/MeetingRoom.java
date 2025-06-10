package finalmission.meetingroom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    private int maximumNumber;

    protected MeetingRoom() {

    }

    public MeetingRoom(String roomName, int maximumNumber) {
        this.roomName = roomName;
        this.maximumNumber = maximumNumber;
    }
}
