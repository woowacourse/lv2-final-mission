package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Time {

    @EmbeddedId
    private final Id id;
    private final String username;
    private final LocalDate date;
    private final LocalTime time;

    @ManyToOne
    private final Room room;

    public Time(String username, LocalDate date, LocalTime time, Room room) {
        this.id = Id.random();
        this.username = username;
        this.date = date;
        this.time = time;
        this.room = room;
    }

    public static List<TimeStatics> calculateStatics(List<Time> times) {
        List<LocalDateTime> dateTimes = times.stream()
                .map(time -> LocalDateTime.of(time.getDate(), time.getTime()))
                .toList();

        return dateTimes.stream()
                .map(dateTime -> new TimeStatics(
                        dateTime,
                        times.stream()
                                .filter(time -> time.isDateTimeOf(dateTime))
                                .map(Time::getUsername)
                                .toList()
                ))
                .toList();
    }

    private boolean isDateTimeOf(LocalDateTime dateTime) {
        return LocalDateTime.of(date, time).equals(dateTime);
    }

    public boolean createdBy(String username) {
        return this.username.equals(username);
    }
}
