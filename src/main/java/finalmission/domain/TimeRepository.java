package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Id> {
    void deleteAllByRoom_IdAndUsername(Id roomId, String username);
}
