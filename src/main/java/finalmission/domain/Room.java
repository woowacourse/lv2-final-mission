package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Room {

    @EmbeddedId
    private final Id id = Id.random();

    @OneToMany(mappedBy = "room")
    private final List<Time> times = new ArrayList<>();

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
