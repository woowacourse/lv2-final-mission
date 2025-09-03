package finalmission.service;

import finalmission.domain.ReservationTime;
import finalmission.dto.MakingReservationTimeRequest;
import finalmission.repository.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime create(MakingReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(null, request.startAt());

        return reservationTimeRepository.save(reservationTime);
    }
}
