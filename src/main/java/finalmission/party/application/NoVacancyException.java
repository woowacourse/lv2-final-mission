package finalmission.party.application;

public class NoVacancyException extends RuntimeException {

    public NoVacancyException(final String message) {
        super(message);
    }
}
