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
public class Vote {

    @EmbeddedId
    private final Id id = Id.random();

    private final LocalDateTime dateTime;

    @ManyToOne
    private final Voter voter;

    @ManyToOne
    private final Room room;

    public Vote(LocalDateTime dateTime, Room room, Voter voter) {
        this.dateTime = dateTime;
        this.voter = voter;
        this.room = room;
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
