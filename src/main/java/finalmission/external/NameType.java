package finalmission.external;

public enum NameType {

    FIRSTNAME, SURNAME, FULLNAME;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
