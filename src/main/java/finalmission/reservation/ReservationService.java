package finalmission.reservation;

import java.time.LocalDate;
import java.util.List;
import finalmission.member.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.util.SubwayLineOverlapChecker;
import finalmission.station.StationRepository;
import finalmission.station.domain.Station;
import finalmission.subway.SubwayRepository;
import finalmission.subway.domain.Subway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SubwayRepository subwayRepository;
    private final StationRepository stationRepository;
    private final MemberRepository memberRepository;

    public boolean isReservable(LocalDate date, Subway subway, Seat seat, Station departStation, Station arriveStation) {
        List<Reservation> existingReservations = reservationRepository.findByDateAndSubwayAndSeat(date, subway, seat);
        if (existingReservations.isEmpty()) {
            return true;
        }

        for (Reservation reservation : existingReservations) {
            if (SubwayLineOverlapChecker.isOverlap(
                    reservation.getDepartStation().getName(),
                    reservation.getArriveStation().getName(),
                    departStation.getName(),
                    arriveStation.getName()
            )) {
                return false;
            }
        }

        return true;
    }

    public Reservation createReservation(Member member, ReservationRequest request) {
        Member foundMember = memberRepository.findByPhoneNumber(member.getPhoneNumber())
                .orElseThrow(IllegalArgumentException::new);

        Subway subway = subwayRepository.findById(Long.valueOf(request.subway_number()))
                .orElseThrow(IllegalArgumentException::new);

        Station departStation = stationRepository.findByName(request.departStation())
                .orElseThrow(IllegalArgumentException::new);

        Station arriveStation = stationRepository.findByName(request.arriveStation())
                .orElseThrow(IllegalArgumentException::new);

        if (!isReservable(request.date(), subway, Seat.valueOf(request.seat()), departStation, arriveStation)) {
            throw new IllegalArgumentException("이미 예약된 좌석입니다. 예약할 수 없습니다.");
        }

        Reservation reservation = new Reservation(
                null,
                foundMember,
                request.date(),
                subway,
                Seat.valueOf(request.seat()),
                departStation,
                arriveStation
        );

        return reservationRepository.save(reservation);
    }

    public List<Reservation> readAllReservation() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Member member, Long id) {
        Member foundMember = memberRepository.findByPhoneNumber(member.getPhoneNumber())
                .orElseThrow(IllegalArgumentException::new);

        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        if (foundReservation.isOwner(foundMember)) {
            throw new IllegalArgumentException("자신의 예약이 아니라서 삭제할 수 없습니다.");
        }

        reservationRepository.delete(foundReservation);
    }

    @Transactional
    public void replaceReservation(Member member, Long id, ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        if (!reservation.isOwner(member)) {
            throw new IllegalArgumentException("자신의 예약이 아니라서 수정할 수 없습니다.");
        }

        Subway subway = subwayRepository.findById(Long.valueOf(request.subway_number()))
                .orElseThrow(IllegalArgumentException::new);

        Station departStation = stationRepository.findByName(request.departStation())
                .orElseThrow(IllegalArgumentException::new);

        Station arriveStation = stationRepository.findByName(request.arriveStation())
                .orElseThrow(IllegalArgumentException::new);

        reservation.updateReservation(request.date(), subway, Seat.valueOf(request.seat()), departStation,
                arriveStation);
    }
}
