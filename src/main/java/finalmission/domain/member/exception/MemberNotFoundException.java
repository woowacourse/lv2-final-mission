package finalmission.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(final String message) {
        super(message);
    }
}
