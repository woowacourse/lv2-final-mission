package finalmission.reservation.service;

import finalmission.exercisecourse.domain.ExerciseCourse;
import finalmission.exercisecourse.infrastructure.ExerciseCourseRepository;
import finalmission.member.domain.Member;
import finalmission.member.dto.LoginMember;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.MyReservationRequest;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.reservationtime.domain.ReservationTime;
import finalmission.reservationtime.infrastructure.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final ExerciseCourseRepository exerciseCourseRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository, MemberRepository memberRepository, ExerciseCourseRepository exerciseCourseRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.memberRepository = memberRepository;
        this.exerciseCourseRepository = exerciseCourseRepository;
    }

    public List<MyReservationResponse> getMyReservations() {
        return reservationRepository.findAll().stream()
                .map(MyReservationResponse::from)
                .toList();
    }

    public MyReservationResponse save(MyReservationRequest request, LoginMember loginMember) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
        ExerciseCourse exerciseCourse = exerciseCourseRepository.findById(request.exerciseCourseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 클래스입니다."));
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

//        존재하는 시간?인지 검증
        Reservation reservation = Reservation.createWithoutId(request.name(), request.date(), member, reservationTime, exerciseCourse);
        Reservation savedReservation = reservationRepository.save(reservation);
        return MyReservationResponse.from(savedReservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservationRepository.delete(reservation);
    }
}
