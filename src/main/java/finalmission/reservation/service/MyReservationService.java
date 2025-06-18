package finalmission.reservation.service;

import finalmission.member.dto.LoginMember;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponse> getMyReservations(LoginMember loginMember) {
        List<Reservation> reservations = reservationRepository.findByMemberId(loginMember.id());
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
