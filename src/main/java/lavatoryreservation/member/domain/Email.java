package lavatoryreservation.member.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

    private String email;

    public Email(String email) {
        this.email = email;
    }

    protected Email() {

    }

    public String getEmailString() {
        return email;
    }

    public boolean isSameMember(Email anotherEmail) {
        return email.equals(anotherEmail.email);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
