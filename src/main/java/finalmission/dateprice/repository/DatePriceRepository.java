package finalmission.dateprice.repository;

import finalmission.dateprice.domain.DatePrice;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatePriceRepository extends JpaRepository<DatePrice, Long> {

    Optional<DatePrice> findByDate(LocalDate date);
}
