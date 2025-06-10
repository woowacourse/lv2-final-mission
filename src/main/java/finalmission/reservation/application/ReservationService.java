package finalmission.reservation.application;

import finalmission.member.application.out.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.popupstore.application.out.PopupStoreRepository;
import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.application.in.dto.Reserve;
import finalmission.reservation.application.out.ReservationRepository;
import finalmission.reservation.application.out.dto.MyReservationWaitingCount;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final PopupStoreRepository popupStoreRepository;

    @Transactional
    public void reserve(final Reserve command) {
        Member reserver = memberRepository.findById(command.reserverId())
                .orElseThrow();
        PopupStore popupStore = popupStoreRepository.findById(command.popupStoreId())
                .orElseThrow();

        LocalDateTime reservedAt = LocalDateTime.now();

        boolean isFulledPopupStore = isFulledPopupStore(popupStore);
        Reservation reservation = Reservation.reserve(
                reserver, popupStore, reservedAt, isFulledPopupStore
        );

        reservationRepository.save(reservation);
    }

    private boolean isFulledPopupStore(final PopupStore popupStore) {
        int currentEnteredCount = reservationRepository.countByPopupStoreIdAndReservationStatus(
                popupStore.getId(), ReservationStatus.ENTERED);

        return popupStore.isFulled(currentEnteredCount);
    }

    public MyReservationWaitingCount getMyWaitingCount(final Long reservationId, final Long memberId) {
        Reservation myReservation = reservationRepository.findById(reservationId)
                .orElseThrow();

        List<Reservation> targetReservations = reservationRepository.findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
                myReservation.getPopupStore(),
                ReservationStatus.WAITING
        );

        for (Reservation reservation : targetReservations) {
            if (reservation.isMyReservation(memberId)) {
                return new MyReservationWaitingCount(
                        reservation.getId(),
                        targetReservations.indexOf(reservation) + 1
                );
            }
        }
        return new MyReservationWaitingCount(reservationId, 0);
    }
}
