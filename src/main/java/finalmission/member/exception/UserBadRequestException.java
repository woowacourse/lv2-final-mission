package finalmission.member.exception;

public class UserBadRequestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "유저에 잘못된 요청입니다.";

    public UserBadRequestException(String message) {
        super(message);
    }

    public UserBadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
