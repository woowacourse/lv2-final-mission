package finalmission.infrastructure.sms;

import java.util.List;

public record CoolSmsRequest(
        List<Message> messages
) {

    public CoolSmsRequest(String phoneNumber, String message) {
        this(List.of(
                Message.from(phoneNumber, message)
        ));
    }
}

record Message(
        String from,
        String to,
        String subject,
        String text
) {

    private static final String FROM_PHONE_NUMBER = "01086216609";
    private static final String MESSAGE_SUBJECT = "[회의실 예약 알림 메시지]";

    public static Message from(String phoneNumber, String message) {
        return new Message(FROM_PHONE_NUMBER, phoneNumber, MESSAGE_SUBJECT, message);
    }
}
