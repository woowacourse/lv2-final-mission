package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Id> {
    List<Vote> getTimesByRoom_Id(Id roomId);

    void deleteAllByRoom_IdAndVoter(Id roomId, Voter voter);

    @Query("""
            SELECT COUNT(*) > 0
            FROM Vote v
            WHERE v.voter = :voter
            AND v.room.id.value = :roomId
            AND v.dateTime IN (:dateTimes)
            """)
    boolean hasDuplicatedVote(String roomId, Voter voter, List<LocalDateTime> dateTimes);
}
