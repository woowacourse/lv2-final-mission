package ordering.entity;

public enum OrderStatus {
    SUCCESS("발주 성공"),
    DELETE("발주 취소");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
