package finalmission.service;

import finalmission.common.client.PublicHolidayClient;
import finalmission.domain.entity.Manager;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.Tour;
import finalmission.dto.ReservationCreateRequest;
import finalmission.dto.ReservationDetailResponse;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.repository.ManagerRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.TourRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;
    private final TourRepository tourRepository;
    private final ReservationRepository reservationRepository;
    private final PublicHolidayClient holidayClient;

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllMemberReservations(Long memberId) {
        List<Reservation> byMemberId = reservationRepository.findByMemberId(memberId);
        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : byMemberId) {
            responses.add(ReservationResponse.from(reservation));
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponse findMemberReservationDetail(Long reservationId) {
        Reservation byId = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        // TODO: 본인만 예약 상세내용 확인가능하도록 변경
        return ReservationDetailResponse.from(byId);
    }

    @Transactional
    public ReservationResponse createMemberReservation(Long memberId, ReservationCreateRequest request) {
        if (holidayClient.checkPublicHoliday(request.date())) {
            throw new IllegalArgumentException("Public holiday is not available");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Manager manager = managerRepository.findById(request.managerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        Reservation saved = reservationRepository.save(new Reservation(
                member,
                manager,
                tour,
                request.date(),
                request.time()
        ));
        return ReservationResponse.from(saved);
    }

    @Transactional
    public ReservationResponse updateMemberReservation(Long reservationId, ReservationUpdateRequest request) {
        Reservation byId = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        Reservation updated = byId.updateReservation(request);
        return ReservationResponse.from(updated);
    }

    @Transactional
    public void deleteMemberReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
