package finalmission.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Name {

    @Column(name = "name", nullable = false)
    private String value;

    public Name(String value) {
        this.value = value;
    }
}
