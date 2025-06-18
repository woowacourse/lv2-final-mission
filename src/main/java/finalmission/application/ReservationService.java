package finalmission.application;

import finalmission.domain.Reservation;
import finalmission.domain.repository.ClassRepository;
import finalmission.domain.repository.ReservaionRepository;
import finalmission.domain.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservaionRepository reservationRepository;
    private final ClassRepository classRepository;
    private final MemberRepository memberRepository;
    private final HolidayService holidayService;

    public Reservation register(final long memberId, final long classId, final LocalDate date, final LocalTime time) {
        if (holidayService.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 예약할 수 없습니다.");
        }

        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id: " + memberId));
        var yogaClass = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수업입니다. id: " + classId));

        var reservation = new Reservation(member, yogaClass, date, time);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getMyReservations(final long memberId) {
        return reservationRepository.findAllByMemberId(memberId);
    }

    public void cancel(final Long reservationId, final Long memberId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. id: " + reservationId));
        if (!reservation.isOwner(memberId)) {
            throw new IllegalArgumentException(
                    String.format("해당 예약의 삭제 권한이 없는 사용자입니다. 예약 id: %d, 사용자 id: %d", reservationId, memberId)
            );
        }
        reservationRepository.deleteById(reservationId);
    }
}
