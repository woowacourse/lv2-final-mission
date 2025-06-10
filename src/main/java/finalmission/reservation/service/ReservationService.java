package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.time.domain.ReservationTime;
import finalmission.time.dto.ReservationTimeResponse;
import finalmission.time.repository.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            MemberRepository memberRepository,
            ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getMember().getName(),
                        reservation.getDate(),
                        ReservationTimeResponse.from(reservation.getTime()))
                )
                .toList();
    }

    public ReservationResponse add(final ReservationRequest reservationRequest) {
        Member memberResult = searchMember(reservationRequest.name());
        ReservationTime reservationTimeResult = searchReservationTime(reservationRequest.timeId());

        Reservation newReservation = new Reservation(
                memberResult,
                reservationRequest.date(),
                reservationTimeResult
        );
        Reservation savedReservation = reservationRepository.add(newReservation);

        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getMember().getName(),
                savedReservation.getDate(),
                ReservationTimeResponse.from(savedReservation.getTime())
        );
    }

    public void deleteById(final Long id) {
        reservationRepository.deleteById(id);
    }

    private Member searchMember(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다!"));
    }

    private ReservationTime searchReservationTime(final Long timeId) {
        return reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("시간 아이디가 존재하지 않습니다!"));
    }
}
