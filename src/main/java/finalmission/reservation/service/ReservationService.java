package finalmission.reservation.service;

import finalmission.customer.domain.Customer;
import finalmission.customer.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final UmbrellaRepository umbrellaRepository;

    public Reservation createReservation(ReservationRequest request) {
        Customer customer = findUser(request.userId());
        Umbrella umbrella = umbrellaRepository.findAvailableUmbrella(request.reservationDate())
                .stream().findFirst().orElseThrow(() -> new IllegalStateException("예약가능한 우산이 없습니다."));

        Reservation withoutId = Reservation.createWithoutId(customer, umbrella, request.reservationDate());

        return reservationRepository.save(withoutId);
    }
    
    public List<Reservation> findReservations(ReservationRequest request){
        Customer customer = findUser(request.userId());
        return reservationRepository.findByCustomerAndReservationDate(customer, request.reservationDate());
    }

    public List<Reservation> findAllReservations(LocalDate reservationDate){
        return reservationRepository.findByReservationDate(reservationDate);
    }

    public void deleteReservation(ReservationRequest request) {
        Customer customer = findUser(request.userId());
        reservationRepository.deleteByCustomerAndReservationDate(customer, request.reservationDate());
    }

    private Customer findUser(long userId){
        return customerRepository
                .findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다. ID : " + userId));
    }
}

