package finalmission.cake.model;

import finalmission.exception.BadRequestException;
import finalmission.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long Id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "time_id")
    @NotNull
    private ReservationTime time;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cake_id")
    @NotNull
    private Cake cake;

    @ManyToOne
    @JoinColumn(name = "flavor_id")
    @NotNull
    private Flavor flavor;

    @ManyToOne
    @JoinColumn(name = "size_id")
    @NotNull
    private Size size;

    @Column(name = "lettering")
    private String lettering;

    public Reservation(LocalDate date, ReservationTime time, Member member, Cake cake, Flavor flavor, Size size,
                       String lettering) {
        validateLettering(lettering);
        this.date = date;
        this.time = time;
        this.member = member;
        this.cake = cake;
        this.flavor = flavor;
        this.size = size;
        this.lettering = lettering;
    }

    private void validateLettering(String lettering) {
        if (lettering.length() > 15) {
            throw BadRequestException.letteringTooLong();
        }
    }

    public void update(Reservation updatedReservation) {
        this.date = updatedReservation.getDate();
        this.time = updatedReservation.getTime();
        this.member = updatedReservation.getMember();
        this.cake = updatedReservation.getCake();
        this.flavor = updatedReservation.getFlavor();
        this.size = updatedReservation.getSize();
        this.lettering = updatedReservation.getLettering();
    }
}
