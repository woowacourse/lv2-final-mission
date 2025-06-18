package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ReservationDateTime dateTime;

    @ManyToOne
    private Member member;

    @Embedded
    private Guest guest;
    private Price price;

    public Reservation(final Long id, final ReservationDateTime dateTime, final Member member, final Guest guest,
                       final Price price) {
        this.id = id;
        this.dateTime = dateTime;
        this.member = member;
        this.guest = guest;
        this.price = price;
    }

    public Reservation() {
    }

    public static Reservation createWithoutId(ReservationDateTime dateTime, Member member, Guest guest,
                                              Price price) {
        return new Reservation(null, dateTime, member, guest, price);
    }

    public boolean isReservedBy(Long memberId) {
        return member.isSameMember(memberId);
    }

    public void updateReservation(ReservationDateTime dateTime, Guest guest) {
        this.dateTime = dateTime;
        this.guest = guest;
    }
}
