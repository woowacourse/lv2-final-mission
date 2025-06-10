package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Room {

    @EmbeddedId
    private final Id id;

    public Room() {
        this.id = Id.random();
    }

    public Time createTime(String username, LocalDate date, LocalTime time) {
        return new Time(username, date, time);
    }
}
