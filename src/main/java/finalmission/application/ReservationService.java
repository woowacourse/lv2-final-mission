package finalmission.application;

import finalmission.application.dto.request.CreateReservationRequest;
import finalmission.application.dto.response.CreateReservationResponse;
import finalmission.application.dto.response.ReservationDetailResponse;
import finalmission.application.support.exception.NotFoundEntityException;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Refrigerator;
import finalmission.domain.RefrigeratorRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final ReservationRepository reservationRepository;
    private final Clock clock;

    @Transactional
    public CreateReservationResponse reserve(Long memberId, String refrigeratorId,
                                             CreateReservationRequest createReservationRequest) {
        Member member = getMember(memberId);
        Refrigerator refrigerator = getRefrigerator(refrigeratorId);
        Reservation reservation = Reservation.create(
                createReservationRequest.monsterEnergy(),
                createReservationRequest.quantity(),
                createReservationRequest.dateTime(),
                refrigerator,
                member,
                LocalDateTime.now(clock)
        );
        reservationRepository.save(reservation);
        return new CreateReservationResponse(reservation.getId());
    }

    public List<ReservationDetailResponse> getReservations(Long memberId, String refrigeratorId) {
        Member member = getMember(memberId);
        Refrigerator refrigerator = getRefrigerator(refrigeratorId);
        List<Reservation> reservations = reservationRepository.findAllByMemberAndRefrigerator(member, refrigerator);
        return reservations.stream()
                .map(reservation -> new ReservationDetailResponse(
                        reservation.getId(),
                        reservation.getMonsterEnergy(),
                        reservation.getQuantity(),
                        reservation.getDateTime()
                ))
                .toList();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundEntityException("해당 사용자가 존재하지 않습니다."));
    }

    private Refrigerator getRefrigerator(String refrigeratorId) {
        return refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new NotFoundEntityException("해당 ID의 냉장고가 존재하지 않습니다."));
    }
}
