package finalmission.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public enum TokenAuthRole {
    USER(Set.of()),
    ADMIN(Set.of(USER));

    private final Set<TokenAuthRole> possibleAccessRole;

    TokenAuthRole(Set<TokenAuthRole> possibleAccessRole) {
        this.possibleAccessRole = possibleAccessRole;
    }

    public static TokenAuthRole findByName(String role) {
        return Arrays.stream(TokenAuthRole.values())
                .filter(tokenAuthRole -> Objects.equals(tokenAuthRole.name(), role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 이름으로 토큰 권한을 찾을 수 없습니다."));
    }

    public boolean hasAccessRole(TokenAuthRole tokenAuthRole) {
        return Objects.equals(this, tokenAuthRole) || this.possibleAccessRole.contains(tokenAuthRole);
    }
}
