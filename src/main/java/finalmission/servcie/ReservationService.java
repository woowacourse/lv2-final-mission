package finalmission.servcie;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Seat;
import finalmission.dto.layer.ReservationCreationContent;
import finalmission.dto.layer.ReservationUpdateContent;
import finalmission.dto.response.FindAllReservationByMember;
import finalmission.dto.response.FindAllReservationBySeat;
import finalmission.dto.response.FindReservationById;
import finalmission.exception.BadRequestException;
import finalmission.exception.NotFoundException;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            MemberRepository memberRepository,
            SeatRepository seatRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = true)
    public List<FindAllReservationBySeat> findAllReservationBySeats(long seatId) {
        Seat seat = getSeatById(seatId);
        List<Reservation> reservations = reservationRepository.findAllBySeat(seat);
        return reservations.stream().map(FindAllReservationBySeat::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FindAllReservationByMember> findAllReservationByMember(long memberId) {
        Member member = getMemberById(memberId);
        List<Reservation> reservations = reservationRepository.findAllByMember(member);
        return reservations.stream().map(FindAllReservationByMember::new).toList();
    }

    @Transactional(readOnly = true)
    public FindReservationById findById(long memberId, long reservationId) {
        Member member = getMemberById(memberId);
        Reservation reservation = getReservationById(reservationId);
        validateMineReservation(member, reservation);
        return new FindReservationById(reservation);
    }

    @Transactional
    public Reservation addReservation(ReservationCreationContent content) {
        Member member = getMemberById(content.memberId());
        Seat seat = getSeatById(content.seatId());

        Reservation reservation = new Reservation(member, seat, content.reason(), content.date());
        validateAlreadyReservation(reservation);
        validateAddPastReservation(reservation);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void updateReservationById(long memberId, ReservationUpdateContent updateContent) {
        Member member = getMemberById(memberId);
        Reservation reservation = getReservationById(updateContent.reservationId());
        validateMineReservation(member, reservation);
        reservation.updateReason(updateContent.reason());
    }

    @Transactional
    public void deleteReservation(long memberId, long reservationId) {
        Member member = getMemberById(memberId);
        Reservation reservation = getReservationById(reservationId);
        validateMineReservation(member, reservation);
        reservationRepository.delete(reservation);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("ID에 해당하는 회원이 존재하지 않습니다."));
    }

    private Seat getSeatById(Long positionId) {
        return seatRepository.findById(positionId).orElseThrow(
                () -> new NotFoundException("ID에 해당하는 자리가 존재하지 않습니다."));
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
                () -> new NotFoundException("ID에 해당하는 예약이 존재하지 않습니다."));
    }

    private void validateAlreadyReservation(Reservation reservation) {
        if (reservationRepository.existsByDate(reservation.getDate())) {
            throw new BadRequestException("이미 예약된 자리에 예약을 시도하고 있습니다.");
        }
    }

    private void validateAddPastReservation(Reservation reservation) {
        if (reservation.isPast()) {
            throw new BadRequestException("과거의 날짜로 예약을 시도하고 있습니다.");
        }
    }

    private void validateMineReservation(Member member, Reservation reservation) {
        if (!member.getId().equals(reservation.getMember().getId())) {
            throw new BadRequestException("존재하지 않는 예약입니다.");
        }
    }
}
