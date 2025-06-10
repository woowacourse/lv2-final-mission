package finalmission.umbrella.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Umbrella {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UmbrellaType umbrellaType;

    @Column(nullable = false)
    private LocalDate registrationDate;

    private Umbrella(Long id, UmbrellaType umbrellaType, LocalDate registrationDate) {
        this.id = id;
        this.umbrellaType = umbrellaType;
        this.registrationDate = registrationDate;
    }

    public static Umbrella createWithoutId(final UmbrellaType umbrellaType, final LocalDate registrationDate) {
        return new Umbrella(null, umbrellaType, registrationDate);
    }
}
