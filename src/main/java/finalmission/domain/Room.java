package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;
    private LocalTime startTime;

    private String description;

    @OneToOne
    private Member manager;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> roomMembers;

    public void addRoomMember(final RoomMember roomMember) {
        roomMembers.add(roomMember);
    }

    public Room(
            final String name,
            final LocalDate startDate,
            final LocalTime startTime,
            final String description,
            final Member manager
    ) {
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.description = description;
        this.manager = manager;
    }
}
