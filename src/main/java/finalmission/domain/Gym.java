package finalmission.domain;

public record Gym(
    String name,
    Address address
) {

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 10;

    public Gym {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            var message = String.format("헬스장 이름은 %d자 이상 %d자 이하여야 합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
            throw new IllegalArgumentException(message);
        }
    }
}
