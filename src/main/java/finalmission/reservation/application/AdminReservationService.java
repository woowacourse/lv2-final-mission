package finalmission.reservation.application;

import finalmission.exception.resource.AlreadyExistException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.reservation.ui.dto.CreateReservationRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse create(final CreateReservationRequest request) {
        final ReservationTime time = reservationTimeRepository.getById(request.timeId());
        final Restaurant restaurant = restaurantRepository.getById(request.restaurantId());
        final Member member = memberRepository.getById(request.memberId());

        return ReservationResponse.from(
                createReservation(request.date(), time, restaurant, member)
        );
    }

    private Reservation createReservation(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant,
            final Member member
    ) {
        final ReservationSlot reservationSlot = new ReservationSlot(date, time, restaurant);

        if (reservationRepository.existsByReservationSlot(reservationSlot)) {
            throw new AlreadyExistException("해당 예약 슬롯에 예약이 있습니다.");
        }

        final Reservation reservation = new Reservation(reservationSlot, member);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteAsAdmin(final Long reservationId) {
        final Reservation reservation = reservationRepository.getById(reservationId);

        reservationRepository.deleteById(reservationId);
        // TODO: 대기자들에게 메일 알람
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
