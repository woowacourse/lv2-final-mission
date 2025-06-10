package finalmission.presentation.service;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.dto.ReservationResponseDto;
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
        Seat seat = findSeat(reservationRegisterDto);

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

    private Seat findSeat(ReservationRegisterDto reservationRegisterDto) {
        return seatRepository.findById(reservationRegisterDto.seatId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));
    }

    private Member findMember(LoginMember loginMember) {
        return memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
