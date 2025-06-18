package finalmission.reservation.dto;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.site.domain.Site;
import java.time.LocalDate;

public record ReservationRequest(
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Long siteId
) {

    public Reservation toReservation(Member member, Site site) {
        return new Reservation(null, this.checkInDate, this.checkOutDate, member, site);
    }
}
