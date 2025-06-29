package finalmission.email.infrastructure.twilio.dto;

import finalmission.reservation.domain.ReservationSlot;
import java.util.List;

public record SendEmailRequest(
        List<Personalization> personalizations,
        EmailFrom from,
        List<EmailContent> content
) {

    private static final String DOMAIN_EMAIL = "wooheiler@gmail.com";

    public static SendEmailRequest confirmReservation(final String emailTo) {
        final List<EmailTo> emailTos = List.of(new EmailTo(emailTo));
        final String subject = "[Heiler Table Support] 음식점 예약이 완료되었습니다.";
        final List<Personalization> personalizations = List.of(
                new Personalization(emailTos, subject)
        );

        final EmailFrom emailFrom = new EmailFrom(DOMAIN_EMAIL);
        final List<EmailContent> emailContents = List.of(
                EmailContent.plainText("음식점 예약이 완료되었습니다. 감사합니다.")
        );

        return new SendEmailRequest(personalizations, emailFrom, emailContents);
    }

    public static SendEmailRequest alarmForWaiting(
            final String emailTo,
            final ReservationSlot reservationSlot
    ) {
        final List<EmailTo> emailTos = List.of(new EmailTo(emailTo));
        final String subject = "[Heiler Table Support] 대기 요청하신 음식점 예약이 비었습니다.";
        final List<Personalization> personalizations = List.of(
                new Personalization(emailTos, subject)
        );

        final EmailFrom emailFrom = new EmailFrom(DOMAIN_EMAIL);
        final List<EmailContent> emailContents = List.of(
                EmailContent.plainText(
                        "현재 "
                                + convertToString(reservationSlot)
                                + " 예약 가능합니다. 감사합니다.")
        );

        return new SendEmailRequest(personalizations, emailFrom, emailContents);
    }

    private static String convertToString(final ReservationSlot reservationSlot) {
        return reservationSlot.getRestaurant().getName() + " "
                + reservationSlot.getDate().toString() + " "
                + reservationSlot.getTime().getStartAt();
    }
}
