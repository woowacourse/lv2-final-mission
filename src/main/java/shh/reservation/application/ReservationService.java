package shh.reservation.application;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shh.alias.application.AliasService;
import shh.alias.domain.Alias;
import shh.common.exception.BadRequestException;
import shh.common.exception.NotFoundException;
import shh.member.domain.Member;
import shh.member.domain.repository.MemberRepository;
import shh.reservation.application.dto.MyReservationResponse;
import shh.reservation.application.dto.ReservationAddRequest;
import shh.reservation.application.dto.ReservationAddResponse;
import shh.reservation.application.dto.ReservationResponse;
import shh.reservation.application.dto.ReservationUpdateRequest;
import shh.reservation.domain.Reservation;
import shh.reservation.domain.ReservationTime;
import shh.reservation.domain.repository.ReservationRepository;
import shh.reservation.domain.repository.ReservationTimeRepository;
import shh.stall.domain.Stall;
import shh.stall.domain.repository.StallRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final StallRepository stallRepository;
    private final AliasService aliasService;

    public ReservationAddResponse reserve(
            final Long memberId,
            final ReservationAddRequest reservationAddRequest
    ) {
        final LocalDate date = reservationAddRequest.date();
        final ReservationTime reservationTime = getReservationTime(reservationAddRequest.timeId());
        final Stall stall = getStall(reservationAddRequest);
        validateDuplicateReservation(date, reservationTime, stall);
        final Member member = getMember(memberId);
        final Alias alias = aliasService.generateAlias(1);
        final Reservation reservation = new Reservation(date, reservationTime, stall, member, alias);
        final Reservation saved = reservationRepository.save(reservation);
        return ReservationAddResponse.from(saved);
    }

    public List<MyReservationResponse> findMyReservation(final Long memberId) {
        final List<Reservation> reservations = reservationRepository.findAllByMemberId(memberId);
        return reservations.stream()
                .map(MyReservationResponse::from)
                .toList();
    }

    public void updateDateAndTime(
            final Long memberId,
            final Long id,
            final ReservationUpdateRequest updateReservationTimeRequest
    ) {
        final Reservation reservation = getReservation(id);
        validateOwnReservation(memberId, reservation);
        validateDuplicateReservation(updateReservationTimeRequest.date(), updateReservationTimeRequest.time(),
                reservation.getStall());
        reservation.updateDateAndTime(updateReservationTimeRequest.date(), updateReservationTimeRequest.time());
    }

    public void deleteReservation(final Long memberId, final Long id) {
        final Reservation reservation = getReservation(id);
        validateOwnReservation(memberId, reservation);
        reservationRepository.delete(reservation);
    }

    public List<ReservationResponse> findReservationByStallId(final Long stallId) {
        final List<Reservation> reservations = reservationRepository.findAllByStallId(stallId);
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    private void validateDuplicateReservation(
            final LocalDate date,
            final ReservationTime reservationTime,
            final Stall stall
    ) {
        if (reservationRepository.existsByDateAndTimeAndStall(date, reservationTime, stall)) {
            throw new BadRequestException("이미 예약되었습니다.");
        }
    }

    private void validateOwnReservation(final Long memberId, final Reservation reservation) {
        if (!reservation.getMember().getId().equals(memberId)) {
            throw new BadRequestException("사용자의 예약이 아닙니다.");
        }
    }

    private Reservation getReservation(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("예약이 존재하지 않습니다."));
    }

    private ReservationTime getReservationTime(final Long timeId) {
        return reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new NotFoundException("예약시간이 존재하지 않습니다."));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    private Stall getStall(final ReservationAddRequest reservationAddRequest) {
        return stallRepository.findById(reservationAddRequest.stallId())
                .orElseThrow(() -> new NotFoundException("대변기칸이 존재하지 않습니다."));
    }
}
