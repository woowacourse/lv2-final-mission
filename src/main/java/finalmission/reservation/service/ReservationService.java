package finalmission.reservation.service;

import finalmission.member.domain.User;
import finalmission.member.repository.UserRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final UmbrellaRepository umbrellaRepository;

    public Reservation createReservation(ReservationCreateRequest request) {
        long reservationCounts = countReservationByDate(request.reservationDate());
        long umbrellaCounts = countUmbrella();

        if(reservationCounts == umbrellaCounts) {
            throw new IllegalStateException("이미 모든 우산이 예약되었습니다 일자 : " + request.reservationDate());
        }

        User findUser = userRepository
                .findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다. ID : " + request.userId()));

        Umbrella findUmbrella = umbrellaRepository.findById(reservationCounts + 1)
                .orElseThrow(() -> new IllegalArgumentException("해당 우산을 찾을 수 없습니다. ID : " + reservationCounts + 1));
        Reservation withoutId = Reservation.createWithoutId(findUser, findUmbrella, request.reservationDate());

        return reservationRepository.save(withoutId);
    }

    private long countUmbrella() {
        return umbrellaRepository.count();
    }

    private long countReservationByDate(LocalDate reservationDate) {
        return reservationRepository.countByReservationDate(reservationDate);
    }


}

