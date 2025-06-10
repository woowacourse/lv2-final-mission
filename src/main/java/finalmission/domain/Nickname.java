package finalmission.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Nickname {

    private String name;

    public Nickname() {
    }

    public Nickname(String name) {
        validateLength(name);
        this.name = name;
    }

    private void validateLength(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new IllegalArgumentException("크루 닉네임은 최소 2글자, 최대 4글자여야합니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Nickname nickname = (Nickname) object;
        return Objects.equals(name, nickname.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
