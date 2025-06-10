package finalmission.reservation.application;

import finalmission.member.application.out.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.popupstore.application.out.PopupStoreRepository;
import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.application.in.dto.Reserve;
import finalmission.reservation.application.out.ReservationRepository;
import finalmission.reservation.domain.Reservation;
import java.time.LocalDateTime;
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
        Reservation reservation = Reservation.reserve(
                reserver, popupStore, reservedAt
        );

        reservationRepository.save(reservation);
    }
}
