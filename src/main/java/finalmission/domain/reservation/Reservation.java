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
import java.time.LocalDateTime;
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

    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    private static final LocalTime END_TIME = LocalTime.of(20, 0);

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

        validateTime(design, date, time);
        validateDesigner(designer, date);
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

    private static void validateTime(final Design design, final LocalDate date, final LocalTime time) {
        LocalDateTime selectedDateAndTime = date.atTime(time);

        if (selectedDateAndTime.isBefore(date.atTime(START_TIME))) {
            throw new IllegalArgumentException("샵 운영 시간 전에는 예약할 수 없습니다.");
        }

        if (selectedDateAndTime.isAfter(date.atTime(END_TIME))) {
            throw new IllegalArgumentException("샵 운영 시간 후에는 예약할 수 없습니다.");
        }

        if (selectedDateAndTime.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("예약은 현재 시간을 기준으로 1시간 이후부터 가능합니다.");
        }

        int turnAroundTime = design.getTurnaroundTime();
        LocalDateTime serviceEndTime = selectedDateAndTime.plusMinutes(turnAroundTime);

        if (serviceEndTime.isAfter(date.atTime(END_TIME))) {
            throw new IllegalArgumentException("서비스 완료 시간이 마감 시간 이후 입니다.");
        }
    }

    private static void validateDesigner(final Designer designer, final LocalDate date) {
        if (designer.isOffDay(date.getDayOfWeek())) {
            throw new IllegalArgumentException("해당 디자이너의 휴무 요일입니다.");
        }
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
