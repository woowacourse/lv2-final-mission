package finalmission.music.reservation.domain;

import finalmission.music.lottery.domain.Lottery;
import finalmission.music.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lottery lottery;

    @ManyToOne
    private Member member;

    private String address;

    public Reservation(final Lottery lottery, final Member member, String address) {
        this.lottery = lottery;
        this.member = member;
        this.address = address;
    }

    public void changeAddress(String newAddress) {
        this.address = newAddress;
    }
}
