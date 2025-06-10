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
    private UserName userName;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    private Owner(
            final Long id,
            final UserName userName,
            final Email email,
            final Password password
    ) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Owner(
            final UserName userName,
            final Email email,
            final Password password
    ) {
        this(null, userName, email, password);
    }

    @Override
    public Role getRole() {
        return Role.OWNER;
    }
}
