package shh.alias.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Alias {

    private String alias;

    public Alias(final String alias) {
        this.alias = alias;
    }
}
