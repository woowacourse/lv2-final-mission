package finalmission.domain.repository;

import finalmission.domain.Car;
import finalmission.domain.Rent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
}
