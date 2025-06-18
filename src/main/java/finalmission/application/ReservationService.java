package finalmission.application;

import finalmission.domain.customer.Customer;
import finalmission.domain.design.Design;
import finalmission.domain.design.DesignRepository;
import finalmission.domain.designer.Designer;
import finalmission.domain.designer.DesignerRepository;
import finalmission.domain.reservation.Reservation;
import finalmission.domain.reservation.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DesignRepository designRepository;
    private final DesignerRepository designerRepository;
    private final PublicHolidayService publicHolidayService;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final DesignRepository designRepository,
            final DesignerRepository designerRepository,
            final PublicHolidayService publicHolidayService
    ) {
        this.reservationRepository = reservationRepository;
        this.designRepository = designRepository;
        this.designerRepository = designerRepository;
        this.publicHolidayService = publicHolidayService;
    }

    public Reservation createReservation(final Customer customer,
                                         final LocalDate date,
                                         final LocalTime time,
                                         final Long designId,
                                         final Long designerId
    ) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new IllegalArgumentException("디자인이 존재하지 않습니다."));

        Designer designer = designerRepository.findById(designerId)
                .orElseThrow(() -> new IllegalArgumentException("디자이너가 존재하지 않습니다."));

        validateSelectedDate(date);

        Reservation reservation = Reservation.register(customer, date, time, design, designer);
        return reservationRepository.save(reservation);
    }

    private void validateSelectedDate(final LocalDate date) {
        publicHolidayService.validatePublicHoliday(date);
    }

    public List<Reservation> getReservationsByCustomer(final Customer customer) {
        return reservationRepository.findReservationByCustomer(customer);
    }

    public void removeReservation(final Customer customer, final Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("예약이 존재하지 않습니다.");
        }

        if (reservation.get().getCustomer().equals(customer)) {
            throw new IllegalArgumentException("해당 예약은 고객의 것이 아닙니다.");
        }

        reservationRepository.delete(reservation.get());
    }
}
