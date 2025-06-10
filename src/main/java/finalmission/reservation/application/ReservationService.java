package finalmission.reservation.application;

import finalmission.email.application.EmailService;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import finalmission.exception.auth.AuthorizationException;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final EmailService emailService;

    @Transactional
    public ReservationResponse create(
            final CreateReservationRequest request,
            final Long memberId
    ) {
        final ReservationTime time = getReservationTime(request.date(), request.timeId());
        final Restaurant restaurant = restaurantRepository.getById(request.restaurantId());
        final Member member = memberRepository.getById(memberId);

        final Reservation reservation = createReservedReservation(
                request.date(), time, restaurant, member
        );
        emailService.sendEmail(SendEmailRequest.confirmReservation(member.getEmail()));
        
        return ReservationResponse.from(reservation);
    }

    private Reservation createReservedReservation(
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

    private ReservationTime getReservationTime(final LocalDate date, final Long timeId) {
        final ReservationTime reservationTime = reservationTimeRepository.getById(timeId);
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime reservationDateTime = LocalDateTime.of(date, reservationTime.getStartAt());
        if (reservationDateTime.isBefore(now)) {
            throw new IllegalArgumentException("예약 시간은 현재 시간보다 이후여야 합니다.");
        }

        return reservationTime;
    }

    @Transactional
    public void deleteIfOwner(final Long reservationId, final Long memberId) {
        final Reservation reservation = reservationRepository.getById(reservationId);
        final Member member = memberRepository.getById(memberId);

        if (!Objects.equals(reservation.getMember(), member)) {
            throw new AuthorizationException("본인이 아니면 예약을 삭제할 수 없습니다.");
        }

        reservationRepository.deleteById(reservationId);
        List<Member> waitingMembers = memberRepository.findAll();
        for (final Member waitingMember : waitingMembers) {
            emailService.sendEmail(SendEmailRequest.waitingAlarm(waitingMember.getEmail()));
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
