package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.reservationTime.domain.ReservationTime;
import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "reservation_time")
    private ReservationTime reservationTime;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "visitor")
    private int visitor;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    protected Reservation(){}

    private Reservation(
            final Member member,
            final Restaurant restaurant,
            final ReservationTime reservationTime,
            final LocalDate date,
            final int visitor,
            final Status status
    ) {
        this.member = member;
        this.restaurant = restaurant;
        this.reservationTime = reservationTime;
        this.date = date;
        this.visitor = visitor;
        this.status = status;
    }

    public static Reservation beforeSave(
            final Member member,
            final Restaurant restaurant,
            final ReservationTime reservationTime,
            final LocalDate date,
            final int visitor,
            final Status status
    ) {
        return new Reservation(member, restaurant, reservationTime, date, visitor, status);
    }

    public void update(
            final Restaurant restaurant,
            final ReservationTime reservationTime,
            final LocalDate date,
            final int visitor
    ) {
        this.restaurant = restaurant;
        this.reservationTime = reservationTime;
        this.date = date;
        this.visitor = visitor;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getVisitor() {
        return visitor;
    }

    public Status getStatus() {
        return status;
    }
}
