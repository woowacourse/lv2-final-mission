package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Toilet;
import finalmission.dto.request.ReservationRequest;
import finalmission.dto.response.ReservationResponse;
import finalmission.exception.CanNotDeleteReservationException;
import finalmission.exception.MemberNotFoundException;
import finalmission.exception.ReservationNotFoundException;
import finalmission.exception.ToiletNotFoundException;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.ReservationRepository;
import finalmission.infrastructure.ToiletRepository;
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

    public List<ReservationResponse> findReservationByToiletIdAndDate(Long toiletId, LocalDate date) {
        return reservationRepository.findByToiletIdAndDate(toiletId, date)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationResponse> findReservationByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return reservationRepository.findByMember(member)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteReservationById(Long memberId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        if (!reservation.getMember().equals(member)) {
            throw new CanNotDeleteReservationException();
        }
        reservationRepository.deleteById(reservationId);
    }
}
