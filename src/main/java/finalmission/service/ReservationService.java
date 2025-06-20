package finalmission.service;

import finalmission.domain.CanceledReservation;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDate;
import finalmission.domain.ReservationTimeSlot;
import finalmission.dto.ReservationCreateRequest;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import finalmission.exception.ForbiddenException;
import finalmission.exception.NotFoundException;
import finalmission.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final CanceledReservationService canceledReservationService;
    private final HolidayService holidayService;

    @Transactional
    public ReservationResponse save(final long memberId, final ReservationCreateRequest reservationCreateRequest) {
        final Member member = memberService.getMemberById(memberId);
        validateHoliday(reservationCreateRequest.reservationDate());
        final ReservationDate reservationDate = new ReservationDate(reservationCreateRequest.reservationDate());
        final ReservationTimeSlot reservationTimeSlot = new ReservationTimeSlot(reservationCreateRequest.startTime(),
                reservationCreateRequest.endTime());
        final Reservation newRes = new Reservation(reservationDate, reservationTimeSlot, member,
                reservationCreateRequest.numberOfPeople());
        final List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        reservations.forEach(reservation -> reservation.validateDuplicate(newRes));
        return ReservationResponse.from(reservationRepository.save(newRes));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByMemberId(final long memberId) {
        final List<Reservation> reservations = reservationRepository.findByMemberIdWithMember(memberId);
        return reservations.stream().map(ReservationResponse::from).toList();
    }

    @Transactional
    public ReservationResponse update(final long reservationId, final long memberId, final ReservationUpdateRequest reservationUpdateRequest) {
        final Member member = memberService.getMemberById(memberId);
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESERVATION_NOT_FOUND));
        validateHoliday(reservationUpdateRequest.reservationDate());
        reservation.updateReservationDate(new ReservationDate(reservationUpdateRequest.reservationDate()));
        reservation.updateNumberOfPeople(reservationUpdateRequest.numberOfPeople());
        reservation.updateReservationTimeSlot(new ReservationTimeSlot(reservationUpdateRequest.startTime(),
                reservationUpdateRequest.endTime()));
        reservation.updateMember(member);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    @Transactional
    public void delete(final long memberId, final long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESERVATION_NOT_FOUND));
        final Member member = memberService.getMemberById(memberId);
        if (!Objects.equals(reservation.getMember(), member)) {
            throw new ForbiddenException(ErrorCode.RESERVATION_DELETE_FORBIDDEN);
        }
        reservationRepository.deleteById(reservationId);
        canceledReservationService.save(CanceledReservation.from(reservation));
    }

    private void validateHoliday(final LocalDate date) {
        if (holidayService.isHoliday(date)) {
            throw new BadRequestException(ErrorCode.HOLIDAY_RESERVATION_NOT_ALLOWED);
        }
    }
}
