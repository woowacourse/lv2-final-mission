package finalmission.mail;

import finalmission.reservation.domain.Reservation;
import org.springframework.mail.SimpleMailMessage;

import java.time.format.DateTimeFormatter;

public class MailContentBuilder {

    public static SimpleMailMessage reservationMailBuild(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservation.getMember().getEmail());
        message.setSubject(String.format(
                """
                [우아테이블] %s 매장 예약 정보 메일
                """, 
                reservation.getRestaurant().getName()
        ));
        message.setText(String.format(
                """
                %s님 %s %s 예약이 완료되었습니다.
                방문 인원: %d명
                """,
                reservation.getMember().getName(),
                reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")),
                reservation.getRestaurant().getName(),
                reservation.getVisitor()
        ));
        return message;
    }
}
