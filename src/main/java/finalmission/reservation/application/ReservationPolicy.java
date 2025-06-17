package finalmission.reservation.application;

import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.application.out.ReservationRepository;
import finalmission.reservation.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationPolicy {

    private final ReservationRepository reservationRepository;

    public boolean isFulled(final PopupStore popupStore) {
        int currentEnteredCount = reservationRepository.countByPopupStoreIdAndReservationStatus(
                popupStore.getId(), ReservationStatus.ENTERED);

        return popupStore.isFulled(currentEnteredCount);
    }
}
