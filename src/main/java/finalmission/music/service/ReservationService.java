package finalmission.music.service;

import finalmission.music.controller.dto.MyReservationResponse;
import finalmission.music.controller.dto.ReservationRequest;
import finalmission.music.controller.dto.ReservationResponse;
import finalmission.music.domain.Album;
import finalmission.music.domain.Lottery;
import finalmission.music.domain.Member;
import finalmission.music.domain.Reservation;
import finalmission.music.repository.AlbumRepository;
import finalmission.music.repository.LotteryRepository;
import finalmission.music.repository.MemberRepository;
import finalmission.music.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LotteryRepository lotteryRepository;
    private final MemberRepository memberRepository;

    public ReservationResponse reserve(final Long lotteryId, final String address, final String memberName) {
        Lottery lottery = lotteryRepository.findById(lotteryId)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 추첨입니다."));

        Member member = memberRepository.findById(memberName)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 멤버입니다."));

        Reservation reservation = reservationRepository.save(new Reservation(lottery, member, address));
        return ReservationResponse.from(reservation);
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
}
