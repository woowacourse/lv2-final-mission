package finalmission.repository;

import finalmission.domain.Meeting;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByCoachId(Long coachId);

    boolean existsMeetingByDateTimeBetween(LocalDateTime overlappedPossibleStartTime, LocalDateTime overlappedPossibleEndTime);

    List<Meeting> findAllByCrewId(Long crewId);

    Optional<Meeting> findByIdAndCrewId(Long meetingId, Long crewId);
}
