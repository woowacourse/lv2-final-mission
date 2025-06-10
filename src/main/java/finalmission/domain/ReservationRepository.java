package finalmission.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMemberAndRefrigerator(Member member, Refrigerator refrigerator);
}
