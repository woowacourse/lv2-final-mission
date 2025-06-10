package finalmission.domain.auth;

import finalmission.domain.customer.CustomerRole;

public record AuthenticationInfo(
        long id,
        CustomerRole role
) {

    public boolean isAdmin() {
        return this.role == CustomerRole.ADMIN;
    }
}
