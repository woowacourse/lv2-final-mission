package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Room {

    @EmbeddedId
    private final Id id;

    @OneToMany(mappedBy = "room")
    private final List<Time> times = new ArrayList<>();

    public Room() {
        this.id = Id.random();
    }

    public Time createTime(String username, LocalDateTime dateTime) {
        Time newTime = new Time(username, dateTime, this);
        this.times.add(newTime);
        return newTime;
    }

    public List<Time> getTimesOf(String username) {
        return times.stream()
                .filter(time -> time.createdBy(username))
                .toList();
    }
}
