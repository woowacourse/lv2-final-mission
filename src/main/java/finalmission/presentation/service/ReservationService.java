package finalmission.presentation.service;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.Seat;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void registerReservation(ReservationRegisterDto reservationRegisterDto, LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Seat seat = seatRepository.findById(reservationRegisterDto.seatId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));

        Reservation reservation = reservationRegisterDto.toReservation(member, seat);
        reservationRepository.save(reservation);
    }

}
