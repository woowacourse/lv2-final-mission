package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Room {

    @EmbeddedId
    private final Id id = Id.random();
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    @OneToMany(mappedBy = "room")
    private final List<Time> times = new ArrayList<>();
    @OneToMany(mappedBy = "room")
    private final List<Voter> voters = new ArrayList<>();

    public Room(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time createTime(Voter voter, LocalDateTime dateTime) {
        validateDateTimeIsInBoundary(dateTime);
        Time newTime = new Time(dateTime, this, voter);
        this.times.add(newTime);
        return newTime;
    }

    public void addMember(Voter voter) {
        voters.add(voter);
    }

    private void validateDateTimeIsInBoundary(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            throw new IllegalArgumentException("시작날짜와 종료날짜 사이어야 합니다.");
        }
        if (time.isBefore(startTime) || time.isAfter(endTime)) {
            throw new IllegalArgumentException("시작시간과 종료시간 사이어야 합니다.");
        }
    }

    public List<Time> getTimesOf(String name) {
        return times.stream()
                .filter(time -> time.createdBy(name))
                .toList();
    }

    public boolean containsNameOf(String name) {
        return voters.stream().anyMatch(member -> member.hasName(name));
    }
}
