package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationTime;
import finalmission.domain.Room;
import finalmission.dto.MakingReservationRequest;
import finalmission.exception.ReservationException;
import finalmission.exception.ReservationTimeException;
import finalmission.exception.RoomException;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import finalmission.repository.RoomRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final HolidayService holidayService;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository,
                              ReservationTimeRepository reservationTimeRepository, HolidayService holidayService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.holidayService = holidayService;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(MakingReservationRequest makingReservationRequest, Member member) {
        checkHoliday(makingReservationRequest.date());
        Room room = roomRepository.findById(makingReservationRequest.roomId()).orElseThrow(() -> {
            throw new RoomException("없는 회의실 입니다.");
        });
        ReservationTime reservationTime = reservationTimeRepository.findById(
                makingReservationRequest.reservationTimeId()).orElseThrow(() -> {
            throw new ReservationTimeException("없는 예약 시간입니다.");
        });

        Reservation reservation = new Reservation(null, makingReservationRequest.date(), member, room, reservationTime);
        return reservationRepository.save(reservation);
    }

    private void checkHoliday(LocalDate date) {

        if(!holidayService.checkHolyDay(date)){
            throw new ReservationException("공휴일에는 예약을 할 수 없습니다.") ;
        }
    }
}
