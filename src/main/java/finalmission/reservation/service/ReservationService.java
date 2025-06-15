package finalmission.reservation.service;

import finalmission.exception.member.MemberNotFoundException;
import finalmission.exception.reservation.ReservationNotFoundException;
import finalmission.exception.toilet.ToiletNotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.toilet.domain.Toilet;
import finalmission.toilet.infrastructure.ToiletRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ToiletRepository toiletRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository,
                              ToiletRepository toiletRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.toiletRepository = toiletRepository;
    }

    public ReservationResponse createReservation(Long memberId, ReservationRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Toilet toilet = toiletRepository.findById(request.toiletId())
                .orElseThrow(ToiletNotFoundException::new);
        Reservation reservation = new Reservation(request.date(), request.startAt(), request.endAt(), member, toilet);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> findReservations(Long toiletId, LocalDate date) {
        return reservationRepository.findByToiletIdAndDate(toiletId, date)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationResponse> findReservationsByMemberId(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return reservationRepository.findByMemberId(memberId)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteReservationById(Long memberId, Long reservationId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);
        reservation.validateOwner(member);
        reservationRepository.deleteById(reservationId);
    }
}
