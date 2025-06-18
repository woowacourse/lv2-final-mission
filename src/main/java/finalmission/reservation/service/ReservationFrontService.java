package finalmission.reservation.service;

import finalmission.common.exception.AlreadyExistException;
import finalmission.common.exception.AuthorizationException;
import finalmission.concert.domain.Concert;
import finalmission.concert.service.detail.ConcertQueryService;
import finalmission.member.auth.vo.MemberInfo;
import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.member.service.detail.MemberQueryService;
import finalmission.payment.service.PaymentFrontService;
import finalmission.payment.service.dto.PaymentApproveRequest;
import finalmission.payment.service.dto.PaymentApproveResponse;
import finalmission.reservation.controller.dto.ReservationChangeRequest;
import finalmission.reservation.controller.dto.ReservationDetailResponse;
import finalmission.reservation.controller.dto.ReservationRequest;
import finalmission.reservation.controller.dto.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.service.detail.ReservationCommandService;
import finalmission.reservation.service.detail.ReservationQueryService;
import finalmission.seat.domain.Seat;
import finalmission.seat.service.detail.SeatQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationFrontService {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;
    private final MemberQueryService memberQueryService;
    private final ConcertQueryService concertQueryService;
    private final SeatQueryService seatQueryService;
    private final PaymentFrontService paymentFrontService;

    @Transactional
    public ReservationResponse create(final Long memberId, final ReservationRequest request) {
        final Member member = memberQueryService.get(memberId);
        final Concert concert = concertQueryService.get(request.concertId());
        final Seat seat = seatQueryService.get(request.seatId());

        if (reservationQueryService.existsByConcertAndSeat(concert, seat)) {
            throw new AlreadyExistException("이미 예약된 좌석입니다.");
        }

        final Reservation reservation = new Reservation(member, concert, seat);
        final Reservation savedReservation = reservationCommandService.create(reservation);

        final PaymentApproveResponse paymentApproveResponse = paymentFrontService.approve(
                new PaymentApproveRequest(
                        request.tid(),
                        request.pgToken(),
                        savedReservation.getId()
                )
        );

        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getMember().getId(),
                savedReservation.getConcert().getId(),
                savedReservation.getSeat().getId()
        );
    }

    public ReservationResponse get(Long id) {
        final Reservation reservation = reservationQueryService.get(id);

        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getId(),
                reservation.getConcert().getId(),
                reservation.getSeat().getId()
        );
    }

    public List<ReservationResponse> getAll() {
        return reservationQueryService.getAll().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getMember().getId(),
                        reservation.getConcert().getId(),
                        reservation.getSeat().getId()
                ))
                .toList();
    }

    public List<ReservationDetailResponse> getDetails(final Long memberId) {
        return reservationQueryService.getDetailsByMemberId(memberId);
    }

    public ReservationResponse update(final MemberInfo memberInfo, final ReservationChangeRequest request) {
        final Reservation reservation = reservationQueryService.get(request.reservationId());

        if (!reservation.getMember().getId().equals(memberInfo.id()) && !(memberInfo.role() == Role.ADMIN)) {
            throw new AuthorizationException("해당 예약을 변경할 권한이 없습니다.");
        }

        if (reservationQueryService.existsByConcertAndSeat(
                reservation.getConcert(),
                seatQueryService.getByVenueAndSeatNumber(reservation.getConcert().getVenue(), request.seatNumber())
        )) {
            throw new AlreadyExistException("이미 예약된 좌석입니다.");
        }

        reservationCommandService.update(reservation, request.seatNumber());

        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getId(),
                reservation.getConcert().getId(),
                reservation.getSeat().getId()
        );
    }

    @Transactional
    public void delete(final MemberInfo memberInfo, final Long id) {
        final Reservation reservation = reservationQueryService.get(id);

        if (!reservation.getMember().getId().equals(memberInfo.id()) && !(memberInfo.role() == Role.ADMIN)) {
            throw new AuthorizationException("해당 예약을 취소할 권한이 없습니다.");
        }

        paymentFrontService.cancel(reservation.getId());

        reservationCommandService.delete(reservation);
    }
}
