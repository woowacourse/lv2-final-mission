package finalmission.music.reservation.service;

import finalmission.music.reservation.controller.dto.MyReservationResponse;
import finalmission.music.reservation.controller.dto.ReservationResponse;
import finalmission.music.lottery.domain.Lottery;
import finalmission.music.member.domain.Member;
import finalmission.music.reservation.domain.Reservation;
import finalmission.music.lottery.repository.LotteryRepository;
import finalmission.music.member.repository.MemberRepository;
import finalmission.music.reservation.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LotteryRepository lotteryRepository;
    private final MemberRepository memberRepository;

    public ReservationResponse reserve(final Long lotteryId, final String address, final String memberName) {
        Lottery lottery = lotteryRepository.findById(lotteryId)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 추첨입니다."));
        validateIsExpired(lottery);

        Member member = memberRepository.findById(memberName)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 멤버입니다."));

        Reservation reservation = reservationRepository.save(new Reservation(lottery, member, address));
        return ReservationResponse.from(reservation);
    }

    private void validateIsExpired(final Lottery lottery) {
        if (lottery.isExpired()) {
            throw new IllegalStateException("[ERROR] 지나간 추첨에 대해서 예약할 수 없습니다.");
        }
    }

    public List<MyReservationResponse> getMemberReservations(final String member) {
        return reservationRepository.findByMember_Name(member).stream()
            .map(MyReservationResponse::from)
            .toList();
    }

    public void deleteMemberReservation(final Long id, final String memberName) {
        reservationRepository.deleteByIdAndMemberName(id, memberName);
    }

    public MyReservationResponse getMemberReservation(final Long id, final String memberName) {
        Reservation reservation = reservationRepository.findByIdAndMemberName(id, memberName)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 멤버에 해당 예약이 존재하지 않습니다."));

        return MyReservationResponse.from(reservation);
    }

    @Transactional
    public void updateMyReservation(final Long id, String memberName, String newAddress) {
        Reservation reservation = reservationRepository.findByIdAndMemberName(id, memberName)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 멤버에 해당 예약이 존재하지 않습니다."));

        reservation.changeAddress(newAddress);
    }
}
