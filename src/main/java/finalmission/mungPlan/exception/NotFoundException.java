package finalmission.mungPlan.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(String targetName, Object value) {
        super("%s를 찾을 수 없습니다. 입력값: %s".formatted(targetName, value));
    }
}
