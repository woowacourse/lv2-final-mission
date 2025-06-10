package finalmission.presentation.service;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationDetailResponseDto;
import finalmission.dto.ReservationRegisterDto;
import finalmission.dto.ReservationResponseDto;
import finalmission.dto.ReservationUpdateDto;
import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.Seat;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void registerReservation(ReservationRegisterDto reservationRegisterDto, LoginMember loginMember) {
        Member member = findMember(loginMember);
        Seat seat = findSeat(reservationRegisterDto.seatId());

        Reservation reservation = reservationRegisterDto.toReservation(member, seat);
        reservationRepository.save(reservation);
    }

    public List<ReservationResponseDto> getMyReservations(LoginMember loginMember) {
        Member member = findMember(loginMember);

        List<Reservation> foundReservations = reservationRepository.findByMember(member);
        return foundReservations.stream()
                .map(reservation -> {
                    Seat seat = reservation.getSeat();

                    return new ReservationResponseDto(
                            reservation.getId(),
                            seat.getRoomName(),
                            seat.getSeatNumber(),
                            reservation.getReservationDate(),
                            reservation.getStartAt(),
                            reservation.getEndAt()
                    );
                })
                .toList();
    }

    public ReservationDetailResponseDto getReservation(Long reservationId, LoginMember loginMember) {
        Member member = findMember(loginMember);
        Reservation reservation = findReservation(reservationId);

        checkAuthorityOfReservation(reservation, member);

        Seat seat = reservation.getSeat();
        return new ReservationDetailResponseDto(
                reservation.getId(),
                seat.getRoomName(),
                seat.getSeatNumber(),
                reservation.getRegisteredAt(),
                reservation.getReservationDate(),
                reservation.getStartAt(),
                reservation.getEndAt()
        );
    }

    @Transactional
    public void updateReservation(Long reservationId, ReservationUpdateDto reservationUpdateDto,
                                  LoginMember loginMember) {
        Member member = findMember(loginMember);
        Reservation reservation = findReservation(reservationId);
        checkAuthorityOfReservation(reservation, member);

        Seat newSeat = findSeat(reservationUpdateDto.seatId());
        Reservation newReservation = reservationUpdateDto.toReservation(newSeat, member);
        reservation.update(newReservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId, LoginMember loginMember) {
        Member member = findMember(loginMember);
        Reservation reservation = findReservation(reservationId);
        checkAuthorityOfReservation(reservation, member);

        reservationRepository.delete(reservation);
    }

    private static void checkAuthorityOfReservation(Reservation reservation, Member member) {
        if (!reservation.isOwnedBy(member)) {
            throw new IllegalArgumentException("해당 예약 내역에 접근할 권한이 없습니다.");
        }
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 내역입니다."));
    }

    private Seat findSeat(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));
    }

    private Member findMember(LoginMember loginMember) {
        return memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
