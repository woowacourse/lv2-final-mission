package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private YogaClass yogaClass;
    private LocalDate date;
    private LocalTime time;

    public Reservation(User user, YogaClass yogaClass, LocalDate date, LocalTime time) {
        this.user = user;
        this.yogaClass = yogaClass;
        this.date = date;
        this.time = time;
    }

    public boolean isOwner(Long userId) {
        return this.user.getId().equals(userId);
    }
}
