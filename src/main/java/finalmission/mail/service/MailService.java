package finalmission.mail.service;

import finalmission.client.MailClient;
import finalmission.client.dto.SendEmailRequest;
import finalmission.mail.dto.SendReservationEmailDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailClient mailClient;

    public MailService(final MailClient mailClient) {
        this.mailClient = mailClient;
    }

    public void sendSuccessToReservationEmail(final SendReservationEmailDto reservationEmailDto) {
        SendEmailRequest emailDto = new SendEmailRequest(
                reservationEmailDto.customerEmail(),
                "숙소 예약이 완료되었습니다.",
                createMessage(reservationEmailDto)
        );

        mailClient.sendEmail(emailDto);
    }

    private String createMessage(final SendReservationEmailDto reservationEmailDto) {
        String message = "%s님, 숙소 예약이 완료되었습니다. \n"
                + "예약 날짜 : %s ~ %s \n"
                + "예약 숙소명 : %s \n"
                + "예약자명 : %s \n"
                + "예약자 전화번호 : %s \n";

        return String.format(message,
                reservationEmailDto.customerName(),
                dateFormat(reservationEmailDto.startDate()),
                dateFormat(reservationEmailDto.endDate()),
                reservationEmailDto.accommodationName(),
                reservationEmailDto.customerName(),
                reservationEmailDto.customerPhoneNumber()
        );
    }

    private String dateFormat(final LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }
}
