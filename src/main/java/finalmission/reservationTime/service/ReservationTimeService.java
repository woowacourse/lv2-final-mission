package finalmission.reservationTime.service;

import finalmission.reservationTime.controller.dto.request.ReservationTimeRequest;
import finalmission.reservationTime.domain.ReservationTime;
import finalmission.reservationTime.repository.JpaReservationTimeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {

    private JpaReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final JpaReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(final ReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.beforeSave(request.bookedAt());
        return reservationTimeRepository.save(reservationTime);
    }
}
