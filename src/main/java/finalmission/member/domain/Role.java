package finalmission.member.domain;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    MEMBER("MEMBER")
    ;

    private final String code;

    Role(final String code) {
        this.code = code;
    }

    public static Role ofCode(final String code) {
        for (Role role : Role.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("역할 code가 유효하지 않습니다: " + code);
    }
}
