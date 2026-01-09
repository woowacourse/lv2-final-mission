package finalmission.infrastructure.repository;

import finalmission.business.model.entity.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findReservationsByMemberId(Long memberId);

    Optional<Reservation> findReservationById(Long id);

    void deleteReservationById(Long id);
}
