package finalmission.infrastructure;

import finalmission.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailGunClient {

    private final RestClient mailRestClient;

    public void sendReservationMail(Reservation reservation) {
        String text = createMailText(reservation);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("from", "Mailgun Sandbox <postmaster@sandbox16abdadf77a046d1b55994b80db6e99a.mailgun.org>");
        builder.part("to", reservation.getMember().getEmail());
        builder.part("subject", "[회의실 예약] 성공적으로 예약이 완료되었습니다.");
        builder.part("text", text);

        mailRestClient.post()
                .uri("/messages")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(builder.build())
                .retrieve()
                .toBodilessEntity();

        log.info("[SMS] 예약 성공 메일 전송 memberId: {}", reservation.getMember().getId());
    }

    private String createMailText(Reservation reservation) {
        return String.format("""
                        예약이 등록되었습니다.
                        
                        예약 정보
                        - 예약자 닉네임 : %s
                        - 예약 회의실 이름 : %s
                        - 예약한 회의 날짜 : %s
                        - 예약한 회의 시작 시간 : %s
                        - 예약한 회의 마감 시간 : %s
                        """,
                reservation.getMember().getNickname(),
                reservation.getRoom().getName(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );
    }
}
