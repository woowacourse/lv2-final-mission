package finalmission.umbrella.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private LocalDate registrationDate;

    private Umbrella(Long id, LocalDate registrationDate) {
        this.id = id;
        this.registrationDate = registrationDate;
    }

    public static Umbrella create(){
        return new Umbrella(null, LocalDate.now());
    }
}
