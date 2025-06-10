package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private final boolean isAnonymous;
    @OneToMany(mappedBy = "room")
    private final List<Vote> votes = new ArrayList<>();
    @OneToMany(mappedBy = "room")
    private final List<Voter> voters = new ArrayList<>();

    public Room(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, boolean isAnonymous) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAnonymous = isAnonymous;
    }

    public void addMember(Voter voter) {
        voters.add(voter);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public List<Vote> getTimesOf(Voter voter) {
        return votes.stream()
                .filter(time -> time.createdBy(voter))
                .toList();
    }

    public boolean containsNameOf(String name) {
        return voters.stream().anyMatch(member -> member.hasName(name));
    }
}
