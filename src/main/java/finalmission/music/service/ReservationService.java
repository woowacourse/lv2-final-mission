package finalmission.music.service;

import finalmission.music.controller.dto.MyReservationResponse;
import finalmission.music.controller.dto.ReservationRequest;
import finalmission.music.controller.dto.ReservationResponse;
import finalmission.music.domain.Album;
import finalmission.music.domain.Member;
import finalmission.music.domain.Reservation;
import finalmission.music.repository.AlbumRepository;
import finalmission.music.repository.MemberRepository;
import finalmission.music.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;

    public ReservationResponse reserve(ReservationRequest request, String memberName) {
        Album album = albumRepository.findById(request.albumId())
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 앨범 id 입니다."));

        Member member = memberRepository.findById(memberName)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 멤버 id 입니다."));

        Reservation reservation = reservationRepository.save(new Reservation(album, member, request.address()));
        return ReservationResponse.from(reservation);
    }

    public List<MyReservationResponse> getMemberReservations(String member) {
        return reservationRepository.findByMember_Name(member).stream()
            .map(MyReservationResponse::from)
            .toList();
    }
}
