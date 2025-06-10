package finalmission.domain.repository;

import finalmission.domain.Car;
import finalmission.domain.Rent;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {

    boolean existsByCarAndDateAndStartTimeGreaterThanEqualAndReturnTimeLessThanEqual(Car car,  LocalDate date, LocalTime start, LocalTime end);
}
