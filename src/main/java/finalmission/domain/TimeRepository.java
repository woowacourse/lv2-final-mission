package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Id> {
    void deleteAllByRoom_IdAndUsername(Id roomId, String username);

    List<Time> getTimesByRoom_Id(Id roomId);
}
