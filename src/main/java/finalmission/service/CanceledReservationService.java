package finalmission.service;

import finalmission.domain.CanceledReservation;
import finalmission.repository.CanceledReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CanceledReservationService {

    private final CanceledReservationRepository canceledReservationRepository;

    public void save(final CanceledReservation canceledReservation) {
        canceledReservationRepository.save(canceledReservation);
    }
}
