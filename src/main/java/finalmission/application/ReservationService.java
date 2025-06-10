package finalmission.application;

import finalmission.domain.Reservation;
import finalmission.domain.repository.ClassRepository;
import finalmission.domain.repository.ReservaionRepository;
import finalmission.domain.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final HolidayService holidayService;

    public Reservation register(final long userId, final long classId, final LocalDate date, final LocalTime time) {
        if (holidayService.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 예약할 수 없습니다.");
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id: " + userId));
        var yogaClass = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수업입니다. id: " + classId));
        ;
        var reservation = new Reservation(user, yogaClass, date, time);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getMyReservations(final long userId) {
        return reservationRepository.findAllByUserId(userId);
    }

    public void cancel(final Long reservationId, final Long userId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. id: " + reservationId));
        if (!reservation.isOwner(userId)) {
            throw new IllegalArgumentException(
                    String.format("해당 예약의 삭제 권한이 없는 사용자입니다. 예약 id: , 사용자 id: ", reservationId, userId)
            );
        }
        reservationRepository.deleteById(reservationId);
    }
}
