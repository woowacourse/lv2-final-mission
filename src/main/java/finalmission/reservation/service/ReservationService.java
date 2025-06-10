package finalmission.reservation.service;

import finalmission.common.exception.InvalidRequestException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.AvailableReservationCount;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.domain.UmbrellaType;
import finalmission.umbrella.infrastructure.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final UmbrellaRepository umbrellaRepository;

    @Transactional
    public ReservationResponse createPendingReservation(final ReservationCreateRequest request, final long memberId){

        Member findMember = findMember(memberId);
        Umbrella findUmbrella = findUmbrella(request.umbrellaId());

        if(countAvailableUmbrellaReservation(request.reservationDate(), findUmbrella) <= 0){
            throw new InvalidRequestException("해당 우산은 모두 예약 되었습니다. 우산 id : " + request.umbrellaId());
        }

        Reservation pendingWithoutId = Reservation.createPendingWithoutId(request.reservationDate(), findMember, findUmbrella);
        Reservation saveReservation = reservationRepository.save(pendingWithoutId);
        return ReservationResponse.from(saveReservation);
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

    public List<AvailableReservationCount> findAvailableUmbrellas(){
        return Arrays.stream(UmbrellaType.values()).map(umbrellaType -> new AvailableReservationCount(
                umbrellaType, umbrellaRepository.countByUmbrellaType(umbrellaType)
        )).toList();
    }
}
