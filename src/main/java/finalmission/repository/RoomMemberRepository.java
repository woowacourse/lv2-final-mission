package finalmission.repository;

import finalmission.domain.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
}
