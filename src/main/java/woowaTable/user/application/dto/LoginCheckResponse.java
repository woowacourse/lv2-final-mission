package woowaTable.user.application.dto;

import woowaTable.user.domain.User;

public record LoginCheckResponse(
        String name
) {
    public static LoginCheckResponse from(final User user) {
        return new LoginCheckResponse(user.getUserName().getValue());
    }
}
