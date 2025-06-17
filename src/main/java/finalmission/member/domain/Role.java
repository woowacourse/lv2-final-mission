package finalmission.member.domain;

public enum Role {
    MEMBER,
    ADMIN;

    public static boolean isAdmin(Role role) {
        return role == ADMIN;
    }
}
