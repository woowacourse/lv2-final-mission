package finalmission.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class WaitingLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "waitingLine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaitingMember> waitingMembers = new ArrayList<>();

    public static WaitingLine makeNewWaiting() {
        return new WaitingLine(null, new ArrayList<>());
    }

    public void addMember(Member member) {
        WaitingMember waitingMember = new WaitingMember(member, this);
        waitingMembers.add(waitingMember);
    }

    public boolean hasMember(Member member) {
        return waitingMembers.stream()
                .anyMatch(wm -> wm.getMember().equals(member));
    }

    public int getSequenceByMember(Member member) {
        for (int i = 0; i < waitingMembers.size(); i++) {
            if (waitingMembers.get(i).getMember().equals(member)) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WaitingLine that = (WaitingLine) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
