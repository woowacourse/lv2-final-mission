package finalmission.dto;


import finalmission.domain.reservation.Reservation;

public record MailRequestDto(String sendTo, String subject, String text) {

    private static final String SEND_TO_COACH_SUBJECT_MESSAGE = "%s(으)로부터 커피챗 예약이 도착했습니다.";
    private static final String SEND_TO_COACH_TEXT_MESSAGE = """
        %s %s에 %s로부터 커피챗 예약이 도착했습니다.
        애플리케이션에 접속해 수락/거절 버튼을 눌러주세요.
        """;

    private static final String SEND_TO_CREW_SUBJECT_MESSAGE = "%s(으)로부터 커피챗 응답이 도착했습니다.";
    private static final String SEND_TO_CREW_TEXT_MESSAGE = """
        커피챗 신청이 %s 되었습니다. 
        """;

    public static MailRequestDto toCoach(Reservation reservation) {
        return new MailRequestDto(
            reservation.getCoach().getEmail(),
            String.format(SEND_TO_COACH_SUBJECT_MESSAGE, reservation.getCrew().getName()),
            String.format(SEND_TO_COACH_TEXT_MESSAGE,
                reservation.getDate().toString(),
                reservation.getReservationTime().getStartAt(),
                reservation.getCrew().getName())
        );
    }

    public static MailRequestDto toCrew(Reservation reservation) {
        return new MailRequestDto(
            reservation.getCrew().getEmail(),
            String.format(SEND_TO_CREW_SUBJECT_MESSAGE, reservation.getCrew().getName()),
            String.format(SEND_TO_CREW_TEXT_MESSAGE,
                reservation.getReservationStatus().getMessage())
        );
    }
}
