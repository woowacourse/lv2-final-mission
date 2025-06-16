package finalmission.reservation.service;

import finalmission.reservation.domain.Reservation;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ReservationMailService {

    private final JavaMailSender javaMailSender;

    public ReservationMailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendReservationMail(Reservation reservation) {
        try {
            javaMailSender.send(ReservationMailContentBuilder.build(reservation));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}