package finalmission.service;

import finalmission.domain.Lecture;
import finalmission.domain.LectureRepository;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.dto.ReservationMineResponse;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import finalmission.exception.LectureException;
import finalmission.exception.MemberException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    public List<ReservationResponse> findReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationMineResponse> findReservationsOfMember(long memberId) {
        return reservationRepository.findAllByMemberId(memberId).stream()
                .map(ReservationMineResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse saveReservation(ReservationRequest request, long memberId) {
        Lecture lecture = lectureRepository.findById(request.lectureId())
                .orElseThrow(() -> new LectureException("운동 클래스 정보를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("회원 정보를 찾을 수 없습니다."));

        Reservation reservation = new Reservation(LocalDate.now(), lecture, member);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    @Transactional
    public void deleteReservationById(long id) {
        reservationRepository.deleteById(id);
    }
}
