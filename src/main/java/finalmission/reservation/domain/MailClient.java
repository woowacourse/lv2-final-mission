package finalmission.reservation.domain;

import finalmission.reservation.domain.vo.ReservationApproval;

public interface MailClient {

    void send(ReservationApproval reservationApproval);

}
