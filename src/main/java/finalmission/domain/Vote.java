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
public class Vote {

    @EmbeddedId
    private final Id id = Id.random();
    private final LocalDateTime dateTime;
    @ManyToOne
    private final Voter voter;
    @ManyToOne
    private final Room room;

    public Vote(LocalDateTime dateTime, Room room, Voter voter) {
        validateCanVoteTo(dateTime, room);
        this.dateTime = dateTime;
        this.voter = voter;
        this.room = room;
        this.room.addVote(this);
    }

    private static void validateCanVoteTo(LocalDateTime dateTime, Room room) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        if (date.isBefore(room.getStartDate()) || date.isAfter(room.getEndDate())) {
            throw new IllegalArgumentException("시작날짜와 종료날짜 사이어야 합니다.");
        }
        if (time.isBefore(room.getStartTime()) || time.isAfter(room.getEndTime())) {
            throw new IllegalArgumentException("시작시간과 종료시간 사이어야 합니다.");
        }
    }

    public static List<VoteStatics> calculateStatics(List<Vote> votes) {
        List<LocalDateTime> dateTimes = votes.stream().map(Vote::getDateTime).toList();

        return dateTimes.stream()
                .map(dateTime -> new VoteStatics(
                        dateTime,
                        votes.stream()
                                .filter(time -> time.isDateTimeOf(dateTime))
                                .map(Vote::getVoterName)
                                .toList()
                ))
                .toList();
    }

    private boolean isDateTimeOf(LocalDateTime dateTime) {
        return this.dateTime.equals(dateTime);
    }

    public boolean createdBy(Voter voter) {
        return this.voter.equals(voter);
    }

    public String getVoterName() {
        return this.voter.getName();
    }
}
