package finalmission.repository;

import finalmission.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(final Long memberId);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.member WHERE r.member.id = :memberId")
    List<Reservation> findByMemberIdWithMember(@Param("memberId") final Long memberId);

}
