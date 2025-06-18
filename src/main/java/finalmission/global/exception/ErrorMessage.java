package finalmission.global.exception;

public enum ErrorMessage {
    INTERNAL_SERVER_ERROR("서버의 문제가 발생했습니다. 다시 시도해 주세요. \n 문제가 계속될 경우 고객센터로 문의주시기 바랍니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
