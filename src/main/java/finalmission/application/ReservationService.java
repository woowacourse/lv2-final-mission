package finalmission.application;

import finalmission.domain.customer.Customer;
import finalmission.domain.customer.CustomerRepository;
import finalmission.domain.design.Design;
import finalmission.domain.design.DesignRepository;
import finalmission.domain.designer.Designer;
import finalmission.domain.designer.DesignerRepository;
import finalmission.domain.reservation.Reservation;
import finalmission.domain.reservation.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final DesignRepository designRepository;
    private final DesignerRepository designerRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final CustomerRepository customerRepository,
            final DesignRepository designRepository,
            final DesignerRepository designerRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.designRepository = designRepository;
        this.designerRepository = designerRepository;
    }

    public Reservation createReservation(final Long customerId,
                                         final LocalDate date,
                                         final LocalTime time,
                                         final Long designId,
                                         final Long designerId
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));

        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new IllegalArgumentException("디자인이 존재하지 않습니다."));

        Designer designer = designerRepository.findById(designerId)
                .orElseThrow(() -> new IllegalArgumentException("디자이너가 존재하지 않습니다."));

        Reservation reservation = Reservation.register(customer, date, time, design, designer);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByCustomerId(final Long customerId) {
        return reservationRepository.findReservationByCustomerCustomerId(customerId);
    }

    public void removeReservation(final Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
