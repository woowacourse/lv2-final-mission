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
public class Time {

    @EmbeddedId
    private final Id id;
    private final String username;
    private final LocalDate date;
    private final LocalTime time;

    public Time(String username, LocalDate date, LocalTime time) {
        this.id = Id.random();
        this.username = username;
        this.date = date;
        this.time = time;
    }
}
