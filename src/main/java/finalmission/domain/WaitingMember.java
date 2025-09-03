package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class WaitingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;

    @ManyToOne
    private WaitingLine waitingLine;

    private LocalDateTime createdAt;

    public WaitingMember(Member member, WaitingLine waitingLine, LocalDateTime createdAt) {
        this.member = member;
        this.waitingLine = waitingLine;
        this.createdAt = createdAt;
    }

    private WaitingMember(Member member, WaitingLine waitingLine) {
        this(null, member, waitingLine, LocalDateTime.now());
    }

    public static WaitingMember create(Member member, WaitingLine waitingLine) {
        return new WaitingMember(member, waitingLine);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WaitingMember that = (WaitingMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
