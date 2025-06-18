package finalmission.seat.service.detail;

import finalmission.seat.domain.Seat;
import finalmission.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SeatCommandService {

    private final SeatRepository seatRepository;

    public Seat create(final Seat seat) {
        return seatRepository.save(seat);
    }
}
