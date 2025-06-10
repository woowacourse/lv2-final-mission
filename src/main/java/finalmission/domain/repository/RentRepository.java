package finalmission.domain.repository;

import finalmission.domain.Car;
import finalmission.domain.Member;
import finalmission.domain.Rent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentRepository extends JpaRepository<Rent, Long> {

    boolean existsByCarAndDateAndStartTimeGreaterThanEqualAndReturnTimeLessThanEqual(Car car,
                                                                                     LocalDate date,
                                                                                     LocalTime start,
                                                                                     LocalTime end);

    @Query("""
            SELECT r
            FROM Rent r
            LEFT JOIN FETCH Car c
                ON r.car.id = c.id
            """)
    List<Rent> findAllWithCar();

    @EntityGraph(attributePaths = {"car", "member"})
    List<Rent> findAllByMember(Member member);

    @Query("""
            SELECT r
            FROM Rent r
            LEFT JOIN FETCH Car c
                ON r.car.id = c.id
            LEFT JOIN FETCH Member m
                ON r.member.id = m.id
            WHERE r.id = :rentId
            """)
    Optional<Rent> findByIdWithCarAndMember(Long rentId);
}
