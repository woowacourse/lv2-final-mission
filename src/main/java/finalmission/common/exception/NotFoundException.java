package finalmission.common.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String className, Long id) {
        super(className + "를 찾을 수 없습니다: " + id);
    }

    public NotFoundException(String className, String str) {
        super(className + "를 찾을 수 없습니다: " + str);
    }
}
