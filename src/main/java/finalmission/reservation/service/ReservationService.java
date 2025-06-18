package finalmission.reservation.service;

import finalmission.auth.dto.LoginMember;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.site.domain.Site;
import finalmission.site.repository.SiteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final SiteRepository siteRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository,
                              SiteRepository siteRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.siteRepository = siteRepository;
    }

    public ReservationResponse create(LoginMember loginMember, ReservationRequest request) {
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Site site = siteRepository.findById(request.siteId())
                .orElseThrow(() -> new IllegalArgumentException("장소 정보가 없습니다."));
        validateDate(request);
        Reservation reservation = request.toReservation(member, site);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public List<MyReservationResponse> findAllMyReservations(LoginMember loginMember) {
        return reservationRepository.findByMemberId(loginMember.id()).stream()
                .map(MyReservationResponse::from)
                .toList();
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse update(LoginMember loginMember, Long reservationId, ReservationRequest request) {
        validateDate(request);
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Site site = siteRepository.findById(request.siteId())
                .orElseThrow(() -> new IllegalArgumentException("장소 정보가 없습니다."));
        Reservation findReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservationRepository.deleteById(findReservation.getId());
        Reservation savedReservation = reservationRepository.save(request.toReservation(member, site));
        return ReservationResponse.from(savedReservation);
    }

    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private void validateDate(ReservationRequest request) {
        LocalDate date = request.checkInDate();
        while (date.isBefore(request.checkOutDate())) {
            if (reservationRepository.existsReservationInDateRangeAndSiteId(
                    request.siteId(), date)) {
                throw new IllegalArgumentException("해당 기간에 이미 예약이 존재합니다.");
            }
            date = date.plusDays(1);
        }
    }
}
