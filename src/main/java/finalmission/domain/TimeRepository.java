package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Id> {
    List<Time> getTimesByRoom_Id(Id roomId);

    void deleteAllByRoom_IdAndVoter(Id roomId, Voter voter);
}
