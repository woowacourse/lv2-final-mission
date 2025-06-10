package woowaTable.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Owner implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    private Owner(
            final Long id,
            final Name name,
            final Email email,
            final Password password
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Owner(
            final Name name,
            final Email email,
            final Password password
    ) {
        this(null, name, email, password);
    }

    @Override
    public Role getRole() {
        return Role.OWNER;
    }
}
