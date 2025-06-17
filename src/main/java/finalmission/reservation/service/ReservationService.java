package finalmission.reservation.service;

import finalmission.member.domain.User;
import finalmission.member.repository.UserRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final UmbrellaRepository umbrellaRepository;

    public Reservation createReservation(ReservationRequest request) {
        long reservationCounts = countReservationByDate(request.reservationDate());
        long umbrellaCounts = countUmbrella();

        if(reservationCounts == umbrellaCounts) {
            throw new IllegalStateException("이미 모든 우산이 예약되었습니다 일자 : " + request.reservationDate());
        }

        User user = findUser(request.userId());
        Umbrella umbrella = findUmbrella(umbrellaCounts + 1);

        Reservation withoutId = Reservation.createWithoutId(user, umbrella, request.reservationDate());

        return reservationRepository.save(withoutId);
    }

    private long countUmbrella() {
        return umbrellaRepository.count();
    }

    private long countReservationByDate(LocalDate reservationDate) {
        return reservationRepository.countByReservationDate(reservationDate);
    }
    
    public List<Reservation> findReservations(ReservationRequest request){
        User user = findUser(request.userId());
        return reservationRepository.findByUserAndReservationDate(user, request.reservationDate());
    }

    public List<Reservation> findAllReservations(LocalDate reservationDate){
        return reservationRepository.findByReservationDate(reservationDate);
    }

    public void deleteReservation(ReservationRequest request) {
        User user = findUser(request.userId());
        reservationRepository.deleteByUserAndReservationDate(user, request.reservationDate());
    }

    private User findUser(long userId){
        return userRepository
                .findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다. ID : " + userId));
    }

    private Umbrella findUmbrella(long umbrellaId) {
        return umbrellaRepository.findById(umbrellaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 우산을 찾을 수 없습니다. ID : " + umbrellaId));
    }
}

