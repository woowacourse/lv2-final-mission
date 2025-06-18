package finalmission.meetingroom.domain;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int maximumNumber;

    protected MeetingRoom() {

    }

    public MeetingRoom(String roomName, int maximumNumber) {
        this.roomName = roomName;
        this.maximumNumber = maximumNumber;
    }
}
