package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Time {

    @EmbeddedId
    private final Id id = Id.random();

    private final LocalDateTime dateTime;

    @ManyToOne
    private final Voter voter;

    @ManyToOne
    private final Room room;

    public Time(LocalDateTime dateTime, Room room, Voter voter) {
        this.dateTime = dateTime;
        this.voter = voter;
        this.room = room;
    }

    public static List<TimeStatics> calculateStatics(List<Time> times) {
        List<LocalDateTime> dateTimes = times.stream().map(Time::getDateTime).toList();

        return dateTimes.stream()
                .map(dateTime -> new TimeStatics(
                        dateTime,
                        times.stream()
                                .filter(time -> time.isDateTimeOf(dateTime))
                                .map(Time::getCreatorName)
                                .toList()
                ))
                .toList();
    }

    private boolean isDateTimeOf(LocalDateTime dateTime) {
        return this.dateTime.equals(dateTime);
    }

    public boolean createdBy(String name) {
        return this.voter.hasName(name);
    }

    public String getCreatorName() {
        return this.voter.getName();
    }
}
