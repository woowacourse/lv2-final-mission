package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "periods")
    private int period;

    @Enumerated(EnumType.STRING)
    private EducationPart educationPart;

    @Builder
    public Crew(String name, String email, String password, int period, EducationPart educationPart) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.period = period;
        this.educationPart = educationPart;
    }
}
