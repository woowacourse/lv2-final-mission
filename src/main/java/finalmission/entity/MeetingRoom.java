package finalmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_room_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String describe;

    @Column(nullable = false)
    private Integer availablePeopleCount;

    protected MeetingRoom() {
    }

    public MeetingRoom(final Long id, final String name, final String describe, final Integer availablePeopleCount) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.availablePeopleCount = availablePeopleCount;
    }

    public MeetingRoom(final String name, final String describe, final Integer availablePeopleCount) {
        this(null, name, describe, availablePeopleCount);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public Integer getAvailablePeopleCount() {
        return availablePeopleCount;
    }
}
