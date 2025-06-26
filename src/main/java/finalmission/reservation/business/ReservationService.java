package finalmission.reservation.business;

import finalmission.holiday.business.HolidayService;
import finalmission.member.database.MemberRepository;
import finalmission.member.model.Member;
import finalmission.reservation.business.dto.request.ReservationCreateRequest;
import finalmission.reservation.business.dto.request.ReservationDeleteRequest;
import finalmission.reservation.business.dto.request.ReservationDetailedGetRequest;
import finalmission.reservation.business.dto.request.ReservationUpdateTreatmentTypeRequest;
import finalmission.reservation.database.ReservationRepository;
import finalmission.reservation.database.TimeRepository;
import finalmission.reservation.model.Reservation;
import finalmission.reservation.model.Time;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final TimeRepository timeRepository;
    private final HolidayService holidayService;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository, TimeRepository timeRepository, HolidayService holidayService) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.timeRepository = timeRepository;
        this.holidayService = holidayService;
    }

    public Reservation createReservation(ReservationCreateRequest reservationCreateRequest) {
        LocalDate date = reservationCreateRequest.date();
        Long timeId = reservationCreateRequest.timeId();
        validateDuplicatedReservation(date, timeId);
        validateHoliday(date);
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간 id입니다."));
        Member member = memberRepository.findByUsername(reservationCreateRequest.username())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버의 아이디입니다."));
        return reservationRepository.save(new Reservation(reservationCreateRequest.treatmentType(), date, time, member));
    }

    private void validateDuplicatedReservation(LocalDate date, Long timeId) {
        if (reservationRepository.existsByDateAndTimeId(date, timeId)) {
            throw new IllegalArgumentException("이미 예약된 시각입니다.");
        }
    }

    private void validateHoliday(LocalDate date) {
        if (holidayService.existsByDate(date)) {
            throw new IllegalArgumentException("진료하지 않는 날짜입니다.");
        }
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findReservationOfPeriod(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findReservationOfPeriod(startDate, endDate);
    }

    public List<Reservation> findMemberReservations(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버의 이름입니다."));
        return reservationRepository.findByMember(member);
    }

    public Reservation findDetailedReservationOfMember(ReservationDetailedGetRequest reservationDetailedGetRequest) {
        Reservation reservation = reservationRepository.findById(reservationDetailedGetRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id입니다."));
        validateSameMember(reservation.getMember().getUsername(), reservationDetailedGetRequest.username());
        return reservation;
    }

    private void validateSameMember(String savedUsername, String requestedUsername) {
        if (savedUsername.equalsIgnoreCase(requestedUsername)) {
            return;
        }
        throw new IllegalArgumentException("해당 멤버의 예약이 아닙니다.");
    }

    @Transactional
    public Reservation changeTreatmentType(ReservationUpdateTreatmentTypeRequest reservationUpdateTreatmentTypeRequest) {
        Reservation reservation = reservationRepository.findById(reservationUpdateTreatmentTypeRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id입니다."));
        validateSameMember(reservation.getMember().getUsername(), reservationUpdateTreatmentTypeRequest.username());
        reservationRepository.delete(reservation);
        return reservationRepository.save(new Reservation(reservationUpdateTreatmentTypeRequest.treatmentType(), reservation.getDate(), reservation.getTime(), reservation.getMember()));
    }

    public void deleteById(ReservationDeleteRequest reservationDeleteRequest) {
        Reservation reservation = reservationRepository.findById(reservationDeleteRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id입니다."));
        validateSameMember(reservation.getMember().getUsername(), reservationDeleteRequest.username());
        reservationRepository.delete(reservation);
    }
}
