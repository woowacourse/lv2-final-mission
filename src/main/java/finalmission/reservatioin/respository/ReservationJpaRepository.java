package finalmission.reservatioin.respository;

import finalmission.omakase.entity.Omakase;
import finalmission.reservatioin.entity.Reservation;
import finalmission.reservatioin.entity.ReservationTime;
import finalmission.reservatioin.entity.ReservationWithNumberOfPeople;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {


    Long countReservationByReservationTimeAndReservationDateAndOmakase(
            ReservationTime reservationTime,
            LocalDate reservationDate,
            Omakase omakase
    );

    @Query(
            """
            select r
            from Reservation r
            join fetch r.customer c
            join fetch r.omakase  o
            where c.id = :customerId
            """
    )
    List<Reservation> findAllByCustomerId(
            @Param("customerId") Long id
    );

    @Query("""
                SELECT new finalmission.reservatioin.entity.ReservationWithNumberOfPeople(
                    r,
                    (SELECT COUNT(r2)
                     FROM Reservation r2
                     WHERE r2.omakase = r.omakase
                       AND r2.reservationDate = r.reservationDate
                       AND r2.reservationTime = r.reservationTime
                       )
                )
                FROM Reservation r
            """)
    List<ReservationWithNumberOfPeople> findAllWithRank(
    );
}
