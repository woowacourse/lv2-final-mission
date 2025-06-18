package finalmission.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToMany(mappedBy = "waitingLine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WaitingMember> waitingMembers = new ArrayList<>();

    @OneToOne
    private Store store;

    private WaitingLine(Store store) {
        this(null, new ArrayList<>(), store);
    }

    public static WaitingLine makeNewWaiting(Store store) {
        return new WaitingLine(store);
    }

    public void addMember(Member member) {
        if (hasMember(member)) {
            throw new IllegalArgumentException("이미 대기 중인 회원입니다.");
        }
        WaitingMember waitingMember = WaitingMember.create(member, this);
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

    public void removeMember(Member member) {
        waitingMembers.removeIf(wm -> wm.getMember().equals(member));
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
