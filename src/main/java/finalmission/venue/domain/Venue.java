package finalmission.venue.domain;

import finalmission.common.exception.InvalidInputException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    public Venue(final String name, final String address) {
        validate(name, address);
        this.name = name;
        this.address = address;
    }

    private void validate(final String name, final String address) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("이름은 null이거나 빈 값일 수 없습니다.");
        }
        if (address == null || address.isBlank()) {
            throw new InvalidInputException("주소는 null이거나 빈 값일 수 없습니다.");
        }
    }
}
