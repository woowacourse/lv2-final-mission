package finalmission.waiting.application;

import finalmission.exception.auth.AuthorizationException;
import finalmission.exception.resource.AlreadyExistException;
import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.reservation.domain.ReservationSlotRepository;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import finalmission.waiting.domain.Waiting;
import finalmission.waiting.domain.WaitingRepository;
import finalmission.waiting.ui.dto.CreateWaitingRequest;
import finalmission.waiting.ui.dto.WaitingResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final ReservationRepository reservationRepository;
    private final WaitingRepository waitingRepository;

    @Transactional
    public WaitingResponse create(
            final CreateWaitingRequest request,
            final Long memberId
    ) {
        final ReservationTime time = getReservationTime(request.date(), request.timeId());
        final Restaurant restaurant = restaurantRepository.getById(request.restaurantId());
        final Member member = memberRepository.getById(memberId);

        return WaitingResponse.from(
                createWaiting(request.date(), time, restaurant, member, restaurant.getMaxReservationCount())
        );
    }

    private Waiting createWaiting(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant,
            final Member member,
            final int maxReservationCount
    ) {
        final ReservationSlot reservationSlot = getReservationSlot(date, time, restaurant, maxReservationCount);
        final Reservation reservation = reservationRepository.findByReservationSlot(reservationSlot)
                .orElseThrow(() -> new ResourceNotFoundException("예약이 없는 상태에서 예약 대기를 추가할 수 없습니다."));

        if (Objects.equals(reservation.getMember(), member)) {
            throw new AlreadyExistException("해당 예약 슬롯에 본인 예약이 있습니다.");
        }

        if (waitingRepository.existsByReservationSlotAndMember(reservationSlot, member)) {
            throw new AlreadyExistException("신청한 예약 대기가 이미 존재합니다.");
        }

        final Waiting waiting = new Waiting(reservationSlot, member);

        return waitingRepository.save(waiting);
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
    public void deleteIfOwner(final Long waitingId, final Long memberId) {
        final Waiting waiting = waitingRepository.getById(waitingId);
        final Member member = memberRepository.getById(memberId);

        if (!Objects.equals(waiting.getMember(), member)) {
            throw new AuthorizationException("본인이 아니면 삭제할 수 없습니다.");
        }

        waitingRepository.deleteById(waitingId);
    }

    @Transactional(readOnly = true)
    public List<WaitingResponse> findAllWaitingByMemberId(final Long memberId) {
        return waitingRepository.findAllWaitingByMemberId(memberId).stream()
                .map(WaitingResponse::from)
                .toList();
    }
}
