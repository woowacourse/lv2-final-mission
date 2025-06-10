package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.popupstore.domain.PopupStore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reserver;

    @JoinColumn(name = "popup_store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopupStore popupStore;

    private LocalDateTime reservedAt;

    private Reservation(final Member reserver,
                        final PopupStore popupStore,
                        final LocalDateTime reservedAt) {
        this.reserver = reserver;
        this.popupStore = popupStore;
        this.reservedAt = reservedAt;
    }

    public static Reservation reserve(
            final Member reserver,
            final PopupStore popupStore,
            final LocalDateTime reservedAt
    ) {
        return new Reservation(reserver, popupStore, reservedAt);
    }
}
