package ordering.entity;

public enum EmailStatus {
    PROCESSING("처리 중"),
    DONE("이메일 전송 완료");

    private final String text;

    EmailStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
