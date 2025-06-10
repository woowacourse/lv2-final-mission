package finalmission.member.domain;

import java.util.Arrays;

public enum MemberRole {

    REGULAR, ADMIN;

    public static MemberRole from(final String name) {
        return Arrays.stream(MemberRole.values())
                .filter(memberRole -> memberRole.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("memberRole을 찾을 수 없습니다."));
    }
}
