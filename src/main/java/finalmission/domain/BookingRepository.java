package finalmission.domain;

import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;

public interface BookingRepository extends ListCrudRepository<Booking, UUID> {

    default Booking getById(final UUID id) {
        return findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약 ID입니다 : " + id));
    }
}
