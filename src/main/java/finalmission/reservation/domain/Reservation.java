package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.popupstore.domain.PopupStore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Reservation(final Member reserver,
                        final PopupStore popupStore,
                        final LocalDateTime reservedAt,
                        final ReservationStatus reservationStatus) {
        this.reserver = reserver;
        this.popupStore = popupStore;
        this.reservedAt = reservedAt;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation reserve(
            final Member reserver,
            final PopupStore popupStore,
            final LocalDateTime reservedAt,
            final boolean isFulledPopupStore
    ) {
        ReservationStatus status = isFulledPopupStore
                ? ReservationStatus.WAITING
                : ReservationStatus.ENTERED;
        return new Reservation(reserver, popupStore, reservedAt, status);
    }

    public boolean isMine(final Long memberId) {
        return reserver.getId().equals(memberId);
    }

    public void leave() {
        if (reservationStatus == ReservationStatus.ENTERED) {
            this.reservationStatus = ReservationStatus.LEFT;
        } else {
            throw new IllegalStateException("예약 상태가 '입장'이 아닙니다.");
        }
    }

    public void enter() {
        if (reservationStatus == ReservationStatus.WAITING) {
            this.reservationStatus = ReservationStatus.ENTERED;
        } else {
            throw new IllegalStateException("예약 상태가 '대기'가 아닙니다.");
        }
    }
}