package finalmission.reservation.application;

import finalmission.email.domain.EmailDomainService;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import finalmission.exception.auth.AuthorizationException;
import finalmission.exception.resource.AlreadyExistException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.reservation.domain.ReservationSlotRepository;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.reservation.ui.dto.CreateReservationRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import finalmission.waiting.domain.Waiting;
import finalmission.waiting.domain.WaitingRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final ReservationRepository reservationRepository;
    private final WaitingRepository waitingRepository;
    private final EmailDomainService emailDomainService;

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public ReservationResponse create(
            final CreateReservationRequest request,
            final Long memberId
    ) {
        final ReservationTime time = getReservationTime(request.date(), request.timeId());
        final Restaurant restaurant = restaurantRepository.getById(request.restaurantId());
        final Member member = memberRepository.getById(memberId);
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
        final ReservationSlot reservationSlot = getReservationSlot(date, time, restaurant, maxReservationCount);
        if (reservationRepository.existsByReservationSlot(reservationSlot)) {
            throw new AlreadyExistException("해당 예약 슬롯에 예약이 있습니다.");
        }
        if (reservationSlot.isFull()) {
            throw new IllegalArgumentException("해당 음식점의 예약이 모두 찼습니다.");
        }

        return reservationRepository.save(new Reservation(reservationSlot, member));
    }

    private ReservationTime getReservationTime(final LocalDate date, final Long timeId) {
        final ReservationTime reservationTime = reservationTimeRepository.getById(timeId);
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime reservationDateTime = LocalDateTime.of(date, reservationTime.getStartAt());
        if (reservationDateTime.isBefore(now)) {
            throw new IllegalArgumentException("예약 시간은 현재 시간보다 이후여야 합니다.");
        }

        return reservationTime;
    }

    private ReservationSlot getReservationSlot(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant,
            final int maxReservationCount
    ) {
        if (!reservationSlotRepository.existsByRestaurantId(restaurant.getId())) {
            return reservationSlotRepository.save(new ReservationSlot(date, time, restaurant, maxReservationCount));
        }
        return reservationSlotRepository.getByRestaurantId(restaurant.getId());
    }

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public void deleteIfOwner(final Long reservationId, final Long memberId) {
        final Reservation reservation = reservationRepository.getById(reservationId);
        final Member member = memberRepository.getById(memberId);
        if (!Objects.equals(reservation.getMember(), member)) {
            throw new AuthorizationException("본인이 아니면 예약을 삭제할 수 없습니다.");
        }

        final ReservationSlot reservationSlot = reservation.getReservationSlot();
        reservationSlot.decreaseCurrentReservationCount();
        reservationRepository.deleteById(reservationId);

        final List<Member> waitingMembers = waitingRepository.findAllWaitingByReservationSlotId(reservationSlot.getId())
                .stream()
                .map(Waiting::getMember)
                .toList();
        for (final Member waitingMember : waitingMembers) {
            emailDomainService.sendEmail(
                    SendEmailRequest.alarmForWaiting(waitingMember.getEmail(), reservationSlot)
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findReservationsByMemberId(final Long memberId) {
        final Member member = memberRepository.getById(memberId);

        return reservationRepository.findAllByMember(member).stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
