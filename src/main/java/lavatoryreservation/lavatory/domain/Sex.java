package lavatoryreservation.lavatory.domain;

public enum Sex {
    MEN("남자"),
    WOMEN("여자"),
    ETC("기타");

    private final String description;

    Sex(String description) {
        this.description = description;
    }

    public boolean isSameSex(Sex otherSex) {
        return this.equals(otherSex);
    }

    public String getDescription() {
        return description;
    }
}
