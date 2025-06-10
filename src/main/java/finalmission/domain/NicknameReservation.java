package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class NicknameReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Embedded
    Nickname nickname;

    public NicknameReservation() {
    }

    public NicknameReservation(Member member, Nickname nickname) {
        this.id = null;
        this.member = member;
        this.nickname = nickname;
    }

    public boolean hasSameMemberId(long memberId) {
        return member.hasSameMemberId(memberId);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Nickname getNickname() {
        return nickname;
    }
}
