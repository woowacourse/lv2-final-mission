package finalmission.external;

import finalmission.common.exception.BusinessException;

public class ExternalException extends BusinessException {

    public ExternalException(final String message) {
        super(message);
    }
}
