package finalmission.service;

import finalmission.domain.Guest;
import finalmission.domain.Member;
import finalmission.domain.Price;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import finalmission.dto.request.ReservationRequest;
import finalmission.dto.request.ReservationUpdateRequest;
import finalmission.exception.NotFoundDateTimeException;
import finalmission.exception.NotFoundReservationException;
import finalmission.exception.UnauthorizedMemberException;
import finalmission.infrastructure.ReservationDateTimeRepository;
import finalmission.infrastructure.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationDateTimeRepository reservationDateTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationDateTimeRepository reservationDateTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationDateTimeRepository = reservationDateTimeRepository;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    public Reservation createReservation(ReservationRequest request, Member member) {
        ReservationDateTime reservationDateTime = reservationDateTimeRepository
                .findById(request.dateTimeId())
                .orElseThrow(NotFoundDateTimeException::new);
        Guest guest = new Guest(request.guest());

        Reservation reservation = Reservation.createWithoutId(
                reservationDateTime,
                member,
                guest,
                Price.WEEKDAY);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(final Long id, final Long memberId) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        validateReservedMember(reservation, memberId);
        reservationRepository.delete(reservation);
    }

    public Reservation updateReservation(final Long id, final Long memberId, final ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        validateReservedMember(reservation, memberId);
        ReservationDateTime reservationDateTime = reservationDateTimeRepository.findById(request.dateTimeId())
                .orElseThrow();
        Guest guest = new Guest(request.guest());
        reservation.updateReservation(reservationDateTime, guest);
        return reservation;
    }

    private void validateReservedMember(Reservation reservation, Long memberId) {
        if (!reservation.isReservedBy(memberId)) {
            throw new UnauthorizedMemberException();
        }
    }
}
