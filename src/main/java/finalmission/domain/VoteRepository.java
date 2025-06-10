package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Id> {
    List<Vote> getTimesByRoom_Id(Id roomId);

    void deleteAllByRoom_IdAndVoter(Id roomId, Voter voter);
}
