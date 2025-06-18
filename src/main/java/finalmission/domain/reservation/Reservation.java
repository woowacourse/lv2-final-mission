package finalmission.domain.reservation;

import finalmission.domain.customer.Customer;
import finalmission.domain.design.Design;
import finalmission.domain.designer.Designer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long reservationId;
    @ManyToOne
    private Customer customer;
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    private Design design;
    @ManyToOne
    private Designer designer;

    private Reservation(final Long reservationId,
                        final Customer customer,
                        final LocalDate date,
                        final LocalTime time,
                        final Design design,
                        final Designer designer) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.design = design;
        this.designer = designer;
    }

    public static Reservation register(final Customer customer,
                                       final LocalDate date,
                                       final LocalTime time,
                                       final Design design,
                                       final Designer designer) {
        return new Reservation(null, customer, date, time, design, designer);
    }

    public static Reservation ofExisting(final Long reservationId,
                                         final Customer customer,
                                         final LocalDate date,
                                         final LocalTime time,
                                         final Design design,
                                         final Designer designer) {
        return new Reservation(reservationId, customer, date, time, design, designer);
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Design getDesign() {
        return design;
    }

    public Designer getDesigner() {
        return designer;
    }
}
