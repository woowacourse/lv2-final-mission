package finalmission.reservation.intrastructure.repository;

import finalmission.common.exception.NotFoundException;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.ui.dto.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JpaReservationRepository reservationRepository;

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Reservation.class.getSimpleName(), id)
        );
    }

    @Override
    public Reservation getByIdAndCrewId(Long id, Long crewId) {
        return reservationRepository.getByIdAndCrew_Id(id, crewId).orElseThrow(
                () -> new NotFoundException(Reservation.class.getSimpleName(), "id:" + id + " crewId:" + crewId)
        );
    }

    @Override
    public List<ReservationResponse> getAllByCrew(Long id) {
        return reservationRepository.findAllByCrew_Id(id)
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    @Override
    public List<ReservationResponse> getAllByCoach(Long id) {
        return reservationRepository.findAllByCoach_Id(id)
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    @Override
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
