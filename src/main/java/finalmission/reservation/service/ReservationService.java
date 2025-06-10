package finalmission.reservation.service;

import finalmission.common.exception.InvalidRequestException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.infrastructure.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final UmbrellaRepository umbrellaRepository;

    @Transactional
    public ReservationResponse createPendingReservation(final ReservationCreateRequest request){
        Member findMember = findMember(request.memberId());
        Umbrella findUmbrella = findUmbrella(request.umbrellaId());

        if(countAvailableUmbrellaReservation(request.reservationDate(), findUmbrella) <= 0){
            throw new InvalidRequestException("해당 우산은 모두 예약 되었습니다. 우산 id : " + request.umbrellaId());
        }

        Reservation pendingWithoutId = Reservation.createPendingWithoutId(request.reservationDate(), findMember, findUmbrella);
        return ReservationResponse.from(pendingWithoutId);
    }

    private Member findMember(final long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidRequestException("유저를 찾을 수 없습니다. id : " + memberId));
    }

    private Umbrella findUmbrella(final long umbrellaId) {
        return umbrellaRepository.findById(umbrellaId)
                .orElseThrow(() -> new InvalidRequestException("우산을 찾을 수 없습니다 id : " + umbrellaId));
    }

    private long countAvailableUmbrellaReservation(final LocalDate reservationDate, final Umbrella umbrella) {
        long reservationCount = reservationRepository.countReservationByReservationDateAndUmbrella(reservationDate, umbrella);
        long count = umbrellaRepository.countByUmbrellaType(umbrella.getUmbrellaType());

        return count - reservationCount;
    }
}
