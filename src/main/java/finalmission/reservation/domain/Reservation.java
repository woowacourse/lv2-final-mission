package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    private int personnel;

    protected Reservation() {
    }

    public Reservation(final Long id, final LocalDateTime reservationDateTime, final Member member, final Restaurant restaurant, final Integer personnel) {
        this.id = id;
        this.reservationDateTime = reservationDateTime;
        this.member = member;
        this.restaurant = restaurant;
        this.personnel = personnel;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public Member getMember() {
        return member;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Integer getPersonnel() {
        return personnel;
    }
}
