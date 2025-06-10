package finalmission.email.infrastructure.twilio.dto;

import java.util.List;

public record SendEmailRequest(
        List<Personalization> personalizations,
        EmailFrom from,
        List<EmailContent> contents
) {

    private static final String DOMAIN_EMAIL = "threepebbles@naver.com";

    public static SendEmailRequest confirmReservation(final String emailTo) {
        final List<EmailTo> emailTos = List.of(new EmailTo(emailTo));
        final String subject = "음식점 예약이 완료되었습니다.";
        final List<Personalization> personalizations = List.of(
                new Personalization(emailTos, subject)
        );

        final EmailFrom emailFrom = new EmailFrom(DOMAIN_EMAIL);
        final List<EmailContent> emailContents = List.of(
                EmailContent.plainText("음식점 예약이 완료되었습니다. 감사합니다.")
        );

        return new SendEmailRequest(personalizations, emailFrom, emailContents);
    }

    public static SendEmailRequest waitingAlarm(final String emailTo) {
        final List<EmailTo> emailTos = List.of(new EmailTo(emailTo));
        final String subject = "음식점 예약이 비었습니다.";
        final List<Personalization> personalizations = List.of(
                new Personalization(emailTos, subject)
        );

        final EmailFrom emailFrom = new EmailFrom(DOMAIN_EMAIL);
        final List<EmailContent> emailContents = List.of(
                EmailContent.plainText("예약이 비어서 추가 예약이 가능합니다. 감사합니다.")
        );

        return new SendEmailRequest(personalizations, emailFrom, emailContents);
    }
}
