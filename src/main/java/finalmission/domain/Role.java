package finalmission.domain;

public enum Role {
    USER, ADMIN;

    public static boolean isAdmin(final Role role) {
        return role.equals(Role.ADMIN);
    }
}
