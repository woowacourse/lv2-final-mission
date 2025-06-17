package finalmission.reservation.application;

import finalmission.email.domain.EmailDomainService;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
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
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final EmailDomainService emailDomainService;

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public ReservationResponse create(final CreateReservationRequest request) {
        final ReservationTime time = reservationTimeRepository.getById(request.timeId());
        final Restaurant restaurant = restaurantRepository.getById(request.restaurantId());
        final Member member = memberRepository.getById(request.memberId());
        final Reservation reservation
                = createReservation(request.date(), time, restaurant, member, restaurant.getMaxReservationCount());
        reservation.getReservationSlot().increaseCurrentReservationCount();

        emailDomainService.sendEmail(
                SendEmailRequest.confirmReservation(member.getEmail())
        );

        return ReservationResponse.from(reservation);
    }

    private Reservation createReservation(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant,
            final Member member,
            final int maxReservationCount
    ) {
        final ReservationSlot reservationSlot = new ReservationSlot(date, time, restaurant, maxReservationCount);

        if (reservationRepository.existsByReservationSlot(reservationSlot)) {
            throw new AlreadyExistException("해당 예약 슬롯에 예약이 있습니다.");
        }

        final Reservation reservation = new Reservation(reservationSlot, member);

        return reservationRepository.save(reservation);
    }

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public void deleteAsAdmin(final Long reservationId) {
        final Reservation reservation = reservationRepository.getById(reservationId);

        reservation.getReservationSlot().decreaseCurrentReservationCount();
        reservationRepository.deleteById(reservationId);

        final List<Member> waitingMembers = memberRepository.findAll();
        for (final Member waitingMember : waitingMembers) {
            emailDomainService.sendEmail(
                    SendEmailRequest.waitingAlarm(waitingMember.getEmail())
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
