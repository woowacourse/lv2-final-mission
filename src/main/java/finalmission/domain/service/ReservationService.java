package finalmission.domain.service;

import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.Trainer;
import finalmission.domain.service.dto.TrainerReservationResponse;
import finalmission.repository.ReservationRepository;
import finalmission.repository.TrainerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrainerRepository trainerRepository;
    private final TimeInject timeInject;

    public ReservationService(ReservationRepository reservationRepository, TrainerRepository trainerRepository,
                              TimeInject timeInject) {
        this.reservationRepository = reservationRepository;
        this.trainerRepository = trainerRepository;
        this.timeInject = timeInject;
    }

    public List<TrainerReservationResponse> getTrainersReservations(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 선생님입니다: " + trainerId));
        List<Reservation> foundReservations = reservationRepository.findByTrainerAndDateAfterOrderByDateAsc(
                trainer,
                timeInject.now().toLocalDate()
        );
        return foundReservations.stream().map(reservation -> TrainerReservationResponse.from(
                reservation.getDate(),
                reservation.reservedTime()
        )).toList();
    }
}
