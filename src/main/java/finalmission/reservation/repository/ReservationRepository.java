package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByMemberId(Long memberId);

    List<Reservation> findAll();

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.site.id = :siteId " +
            "AND r.checkInDate <= :targetDate " +
            "AND r.checkOutDate >= :targetDate")
    boolean existsReservationInDateRangeAndSiteId(@Param("siteId") Long siteId,
                                                  @Param("targetDate") LocalDate targetDate);
}
