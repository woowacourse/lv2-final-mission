package finalmission.repository;

import finalmission.domain.RoomMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByMemberId(Long memberId);

    Optional<RoomMember> findByRoomIdAndMemberId(Long roomId, Long memberId);

    void deleteByRoomId(Long roomId);
}
