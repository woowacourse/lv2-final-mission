package finalmission.planning.application;

import finalmission.planning.auth.ui.dto.CurrentUserInfo;
import finalmission.planning.domain.Reservation;
import finalmission.planning.domain.ReservationSlot;
import finalmission.planning.domain.User;
import finalmission.planning.exception.NotFoundException;
import finalmission.planning.infra.repository.ReservationRepository;
import finalmission.planning.infra.repository.ReservationSlotRepository;
import finalmission.planning.infra.repository.UserRepository;
import finalmission.planning.ui.dto.request.CreateReservationRequest;
import finalmission.planning.ui.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationSlotRepository reservationSlotRepository;

    public ReservationResponse registerReservation(CreateReservationRequest request,  CurrentUserInfo currentUserInfo) {
        User user = userRepository.findById(currentUserInfo.id())
                .orElseThrow(() -> new NotFoundException("User", currentUserInfo.id().toString()));

        ReservationSlot reservationSlot = reservationSlotRepository.findById(request.reservationSlotId())
                .orElseThrow(() -> new NotFoundException("ReservationSlot", request.reservationSlotId().toString()));

        Reservation saved = reservationRepository.save(new Reservation(user, reservationSlot));
        return ReservationResponse.from(saved);
    }
}
