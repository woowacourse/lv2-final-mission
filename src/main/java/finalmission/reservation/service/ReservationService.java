package finalmission.reservation.service;

import finalmission.client.SpcdeInfoClient;
import finalmission.client.dto.SpcdeInfoResponse.Item;
import finalmission.client.dto.SpcdeInfoResponseWrapper;
import finalmission.common.exceptionHandler.HolidayException;
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
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final ExerciseCourseRepository exerciseCourseRepository;
    private final SpcdeInfoClient spcdeInfoClient;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository, MemberRepository memberRepository, ExerciseCourseRepository exerciseCourseRepository, SpcdeInfoClient spcdeInfoClient) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.memberRepository = memberRepository;
        this.exerciseCourseRepository = exerciseCourseRepository;
        this.spcdeInfoClient = spcdeInfoClient;
    }

    @Transactional(readOnly = true)
    public List<MyReservationResponse> getMyReservations() {
        return reservationRepository.findAll().stream()
                .map(MyReservationResponse::from)
                .toList();
    }

    @Transactional
    public MyReservationResponse save(MyReservationRequest request, LoginMember loginMember) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
        ExerciseCourse exerciseCourse = exerciseCourseRepository.findById(request.exerciseCourseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 클래스입니다."));
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String solYear = String.format("%04d", request.date().getYear());
        String solMonth = String.format("%02d", request.date().getMonthValue());
        SpcdeInfoResponseWrapper response = null;
        try {
            response = spcdeInfoClient.holidayInfoAPI(solYear, solMonth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String targetDate = request.date().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        validateHoliday(response, Integer.parseInt(targetDate));
        Reservation reservation = Reservation.createWithoutId(request.name(), request.date(), member, reservationTime, exerciseCourse);
        Reservation savedReservation = reservationRepository.save(reservation);
        return MyReservationResponse.from(savedReservation);
    }

    private void validateHoliday(SpcdeInfoResponseWrapper response, int targetDate) {
        boolean isHoliday = false;

        List<Item> items = response.response()
                .body()
                .items()
                .item();

        if (!items.isEmpty()) {
            for (Item item : items) {
                if (item.locdate().equals(targetDate)) {
                    isHoliday = true;
                }
            }
        }
        if (isHoliday) {
            throw new HolidayException("공휴일은 예약할 수 없습니다.");
        }
    }

    @Transactional
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservationRepository.delete(reservation);
    }
}
