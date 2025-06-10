package finalmission.domain.reservation.service;

import finalmission.domain.member.entity.Member;
import finalmission.domain.member.exception.MemberNotFoundException;
import finalmission.domain.reservation.entity.Reservation;
import finalmission.domain.reservation.exception.DuplicateReservationException;
import finalmission.domain.reservation.exception.ReservationNotFoundException;
import finalmission.infrastructure.member.JpaMemberRepository;
import finalmission.infrastructure.openapi.HolidayApi;
import finalmission.infrastructure.reservation.JpaReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final JpaReservationRepository reservationRepository;
    private final JpaMemberRepository memberRepository;
    private final HolidayApi holidayApi;

    @Transactional
    public Reservation create(
            final String name,
            final String phoneNumber,
            final String lesson,
            final LocalDate date,
            final LocalTime time) {
        final Member member = memberRepository.findByNameAndPhoneNumber(name, phoneNumber);
        final Reservation reservation = new Reservation(member, lesson, date, time);
        checkDuplicateReservation(lesson, date, time);
        final String response = holidayApi.requestHoliday();
        final Reservation savedReservation = reservationRepository.save(reservation);

        return savedReservation;
    }

    @Transactional
    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }

    @Transactional
    public void updateReservation(final Reservation reservation, final LocalDate date, final LocalTime time) {
        reservation.update(date, time);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getCurrentSituations() {
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Reservation> getMyReservations(final Long id) {
        final Member member = checkMemberPresence(id);

        return reservationRepository.findByMember(member);
    }

    @Transactional(readOnly = true)
    public Reservation findReservation(final Long id) {
        final Reservation reservation = checkReservationPresence(id);

        return reservation;
    }

    private Reservation checkReservationPresence(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("일치하는 예약이 존재하지 않습니다."));
    }

    private Member checkMemberPresence(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("일치하는 회원이 존재하지 않습니다."));
    }

    private void checkDuplicateReservation(final String lesson, final LocalDate date, final LocalTime time) {
        if (reservationRepository.existsByLessonAndDateAndTime(lesson, date, time)) {
            throw new DuplicateReservationException("해당 수업에 대한 예약이 이미 존재합니다.");
        }
    }
}
