package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Room {

    private static final int MAXIMUM_PARTICIPANTS_SIZE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;
    private LocalTime startTime;

    private String description;

    @ManyToOne
    private Member manager;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> roomMembers = new ArrayList<>();

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

    public boolean isFull() {
        return roomMembers.size() >= MAXIMUM_PARTICIPANTS_SIZE;
    }

    public boolean isJoined(final Member member) {
        return roomMembers.stream()
                .anyMatch(roomMember -> roomMember.getMember().equals(member));
    }

    public boolean isGameStarted() {
        final LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime.isBefore(LocalDateTime.now());
    }
}
