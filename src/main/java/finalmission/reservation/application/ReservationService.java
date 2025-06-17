package finalmission.reservation.application;

import finalmission.member.application.out.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.popupstore.application.PopupStorePolicy;
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
    private final PopupStorePolicy popupStorePolicy;

    @Transactional
    public void reserve(final Reserve command) {
        Member reserver = memberRepository.findById(command.reserverId())
                .orElseThrow();
        PopupStore popupStore = popupStoreRepository.findById(command.popupStoreId())
                .orElseThrow();

        LocalDateTime reservedAt = LocalDateTime.now();

        boolean isFulled = popupStorePolicy.isFulled(popupStore);
        Reservation reservation = Reservation.reserve(
                reserver, popupStore, reservedAt, isFulled
        );

        reservationRepository.save(reservation);
    }

    public MyReservationWaitingCount getMyWaitingCount(final Long reservationId, final Long memberId) {
        Reservation myReservation = reservationRepository.findById(reservationId)
                .orElseThrow();

        List<Reservation> reservationsOfTargetPopupStore = reservationRepository.findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
                myReservation.getPopupStore(),
                ReservationStatus.WAITING
        );

        for (Reservation reservationOfTargetPopupStore : reservationsOfTargetPopupStore) {
            if (reservationOfTargetPopupStore.isMine(memberId)) {
                return new MyReservationWaitingCount(
                        reservationOfTargetPopupStore.getId(),
                        reservationsOfTargetPopupStore.indexOf(reservationOfTargetPopupStore) + 1
                );
            }
        }
        return new MyReservationWaitingCount(reservationId, -1);
    }

    @Transactional
    public void leave(final Long reservationId, final Long memberId) {
        Reservation myReservation = reservationRepository.findById(reservationId)
                .orElseThrow();

        List<Reservation> reservationsOfTargetPopupStore = reservationRepository.findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
                myReservation.getPopupStore(),
                ReservationStatus.ENTERED
        );

        int i;
        for (i = 0; i < reservationsOfTargetPopupStore.size(); i++) {
            Reservation reservation = reservationsOfTargetPopupStore.get(i);
            if (reservation.isMine(memberId)) {
                reservation.leave();
            }
        }

        List<Reservation> waitings = reservationRepository.findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
                myReservation.getPopupStore(),
                ReservationStatus.WAITING
        );
        waitings.getFirst().enter();
    }
}
