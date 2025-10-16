package finalmission.music.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @Column(unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member(String name, Role role) {
        this.name = name;
        this.role = role;
    }
}
