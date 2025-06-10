package finalmission.user.domain.vo;

import java.util.Arrays;

public enum Role {
    CUSTOMER,
    OWNER,
    ;

    public static Role from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 ROLE 정보 입니다."));
    }
}
