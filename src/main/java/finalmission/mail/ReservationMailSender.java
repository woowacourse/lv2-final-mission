package finalmission.mail;

import finalmission.reservation.domain.Reservation;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ReservationMailSender {

    private final JavaMailSender javaMailSender;

    public ReservationMailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendReservationMail(Reservation reservation) {
        try {
            javaMailSender.send(MailContentBuilder.reservationMailBuild(reservation));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}