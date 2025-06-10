package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Booking {

    @Id
    private final UUID id = UUID.randomUUID();
    @ManyToOne
    private Member member;
    @ManyToOne
    private Gym gym;
    @Embedded
    private BookingDate date;

    public Booking(final Member member, final Gym gym, final BookingDate date) {
        this.member = member;
        this.gym = gym;
        this.date = date;
    }
}
