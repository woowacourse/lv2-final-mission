package finalmission.planning.application;

import finalmission.planning.domain.Reservation;
import finalmission.planning.domain.ReservationSlot;
import finalmission.planning.domain.User;
import finalmission.planning.exception.NotFoundException;
import finalmission.planning.infra.repository.ReservationRepository;
import finalmission.planning.infra.repository.ReservationSlotRepository;
import finalmission.planning.infra.repository.UserRepository;
import finalmission.planning.ui.dto.request.CreateReservationRequest;
import finalmission.planning.ui.dto.response.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationSlotRepository reservationSlotRepository;

    public ReservationResponse registerReservation(CreateReservationRequest request,  Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId.toString()));

        ReservationSlot reservationSlot = reservationSlotRepository.findById(request.reservationSlotId())
                .orElseThrow(() -> new NotFoundException("ReservationSlot", request.reservationSlotId().toString()));

        Reservation saved = reservationRepository.save(new Reservation(user, reservationSlot));
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> getReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return ReservationResponse.from(reservations);
    }
}
